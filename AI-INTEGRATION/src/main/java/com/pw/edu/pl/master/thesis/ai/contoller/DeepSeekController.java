package com.pw.edu.pl.master.thesis.ai.contoller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pw.edu.pl.master.thesis.ai.client.issue.IssueDetailsClient;
import com.pw.edu.pl.master.thesis.ai.dto.ai.ChatMessage;
import com.pw.edu.pl.master.thesis.ai.dto.issue.issuedetails.IssueDetails;
import com.pw.edu.pl.master.thesis.ai.model.ai.deepseek.DeepSeekChatRequest;
import com.pw.edu.pl.master.thesis.ai.model.ai.deepseek.DeepSeekChatResponse;
import com.pw.edu.pl.master.thesis.ai.service.DeepSeekService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;


@RestController
@RequestMapping("/api/wut/deepseek")
@RequiredArgsConstructor
public class DeepSeekController {

    private final DeepSeekService deepSeekService;
    private final DeepSeekChatModel chatModel;
    private final IssueDetailsClient issueDetailsClient;
    private final ObjectMapper objectMapper;
    private final ChatClient deepSeekClient;


    @GetMapping("/ollama/chat")
    public String deepSeekPrompt(@RequestBody ChatMessage message) {
        return deepSeekClient.prompt().user(message.getContent()).call().content();
    }


    @GetMapping("/generate")
    public Map generate(@RequestParam(value = "message",
            defaultValue = "How to connect with User on What's app") String message) {
        return Map.of("generation", chatModel.call(message));
    }

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "How to connect with User on What's app") String message) {
        var prompt = new Prompt(new UserMessage(message));
        return chatModel.stream(prompt);
    }

    @PostMapping("/chat")
    public ResponseEntity<DeepSeekChatResponse> chat(@RequestBody DeepSeekChatRequest request) {
        DeepSeekChatResponse result = deepSeekService.chat(request);
        return ResponseEntity.ok(result);
    }

    /** NEW: No @RequestParam. Uses only IssueDetails as context for the AI. */
    @GetMapping(value = "/issue/{issueKey}", produces = MediaType.APPLICATION_JSON_VALUE)
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

        // 3) Call the model
        String generation = chatModel.call(prompt);

        // 4) Return both the generation and the details payload
        return ResponseEntity.ok(
                Map.of(
                        "issueKey", issueKey,
                        "details", details,
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
