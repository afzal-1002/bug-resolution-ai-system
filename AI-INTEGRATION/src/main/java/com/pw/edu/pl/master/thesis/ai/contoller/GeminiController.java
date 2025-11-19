package com.pw.edu.pl.master.thesis.ai.contoller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pw.edu.pl.master.thesis.ai.client.issue.IssueDetailsClient;
import com.pw.edu.pl.master.thesis.ai.client.jira.JiraIssueClient;
import com.pw.edu.pl.master.thesis.ai.dto.ai.ChatMessage;
import com.pw.edu.pl.master.thesis.ai.dto.issue.issuedetails.IssueDetails;
import com.pw.edu.pl.master.thesis.ai.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/wut/ai/gemini")
@RequiredArgsConstructor
public class GeminiController {


    private final GeminiService geminiService;
    private final IssueDetailsClient issueDetailsClient;
    private final JiraIssueClient jiraIssueClient;
    private final ObjectMapper objectMapper;

    @PostMapping("/chat")
    public String chat(@RequestBody ChatMessage message) {
        return geminiService.getAiResponse(message);
    }

    @GetMapping(value = "/generate/{issueKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> generateFromIssue(@PathVariable String issueKey) {
        // 1) Fetch compact details from your own service
        IssueDetails details = issueDetailsClient.getIssueDetails(issueKey);

        // 2) Build a deterministic prompt from details only (no user message)
        String prettyDetailsJson = toPrettyJson(details);
        String prompt = """
                You are an expert Jira assistant.
                Analyze the following IssueDetails JSON and produce:
                - A brief summary
                - Top 3 next actions (bulleted)
                - Risks / blockers
                - How long it will take to resolve the issue
                - Any missing information to request

                --- IssueDetails JSON ---
                %s
                --- End JSON ---
                """.formatted(prettyDetailsJson);

        ChatMessage  message = new ChatMessage();
        message.setContent(prompt);
        // 3) Call the model
        String generation = geminiService.getAiResponse(message);

        // 4) Return both the generation and the details payload
        return ResponseEntity.ok(
                Map.of(
                        "issueKey", issueKey,
                        "details", details,
                        "generation", generation
                )
        );
    }


    @GetMapping(value = "/generate-with-attachment/{issueKey}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> generateFromIssueWithAttachment(@PathVariable String issueKey) {

        // 1) get issue details (title, desc, comments, etc.)
        IssueDetails details = issueDetailsClient.getIssueDetails(issueKey);
        String prettyDetailsJson = toPrettyJson(details);

        // 2) try to get first attachment from Issues service
        ResponseEntity<byte[]> attachmentResp = null;
        byte[] attachmentBytes = null;
        String attachmentFilename = null;
        String attachmentMime = null;

        try {
            attachmentResp = jiraIssueClient.downloadFirstAttachment(issueKey);
            attachmentBytes = attachmentResp.getBody();
            if (attachmentBytes != null && attachmentBytes.length > 0) {
                // filename
                ContentDisposition cd = attachmentResp.getHeaders().getContentDisposition();
                attachmentFilename = (cd != null && cd.getFilename() != null)
                        ? cd.getFilename()
                        : "attachment.bin";
                // mime
                MediaType mt = attachmentResp.getHeaders().getContentType();
                attachmentMime = (mt != null) ? mt.toString() : "application/octet-stream";
            }
        } catch (Exception ex) {
            // if no attachment or error – we just continue without it
            attachmentBytes = null;
        }

        // 3) build prompt
        StringBuilder prompt = new StringBuilder("""
            You are an expert Jira assistant.
            Analyze the following IssueDetails JSON and produce:
            - A brief summary
            - Top 3 next actions (bulleted)
            - Risks / blockers
            - How long it will take to resolve the issue
            - Any missing information to request

            --- IssueDetails JSON ---
            %s
            --- End JSON ---
            """.formatted(prettyDetailsJson));

        // 4) if we have an attachment, add it (as base64 for LLM)
        String attachmentBase64 = null;
        if (attachmentBytes != null && attachmentBytes.length > 0) {
            attachmentBase64 = java.util.Base64.getEncoder().encodeToString(attachmentBytes);
            prompt.append("""
                
                --- Attachment info ---
                filename: %s
                mimeType: %s
                Below is the file content encoded in base64. If it's an image/screenshot, describe what you see
                and relate it to the Jira issue analysis.
                
                BASE64_START
                %s
                BASE64_END
                """.formatted(attachmentFilename, attachmentMime, attachmentBase64));
        } else {
            prompt.append("""
                
                (No attachments available for this issue.)
                """);
        }

        // 5) call your AI
        ChatMessage message = new ChatMessage();
        message.setContent(prompt.toString());
        String generation = geminiService.getAiResponse(message);

        // 6) build response
        return ResponseEntity.ok(
                Map.of(
                        "issueKey", issueKey,
                        "details", details,
                        "hasAttachment", attachmentBytes != null,
                        "attachmentName", attachmentFilename,
                        "attachmentMime", attachmentMime,
                        // DON'T return the whole base64 if it's huge – but you can, for debug:
                        "generation", generation
                )
        );
    }


    private String toPrettyJson(Object value) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (Exception e) {
            return String.valueOf(value);
        }
    }
}
