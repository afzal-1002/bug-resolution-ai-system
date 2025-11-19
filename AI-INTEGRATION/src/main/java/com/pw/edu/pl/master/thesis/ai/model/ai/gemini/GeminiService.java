//package com.ii.pw.edu.pl.master.thesis.ai.model.ai.gemini;//package com.pl.edu.wut.master.thesis.bug.model.ai.gemini;
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//
//import java.time.OffsetDateTime;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//@RequiredArgsConstructor @Slf4j
//public class GeminiService {
//
//    private final Jir jiraIssueService;
//    private final JiraCommentService jiraCommentService;
//    private final GeminiClient geminiClient;
//    private final IssueMapper issueMapper;
//    private final GeminiProperties geminiProperties;
//    private final RestTemplate restTemplate;
//    private final String GENERATE_CONTENT_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent";
//
//
//    public GeminiResponse generateAnalysisForIssue(String issueKey) {
//        OffsetDateTime start = OffsetDateTime.now();
//
//        IssueResponse issueResponse = jiraIssueService.getIssueByKeyJira(issueKey);
//        String promptText = generateIssueSummaryText(issueResponse);
//        promptText = promptText + "\n\n How long it will take to this issue?";
//
//        System.out.println("Prompt text: " + promptText);
//
//        GeminiResponse response = geminiClient.getAnalysis(promptText);
//
//        OffsetDateTime end = OffsetDateTime.now();
//        long durationSeconds = java.time.Duration.between(start, end).getSeconds();
//
//        response.setStartTime(start);
//        response.setEndTime(end);
//        response.setTimeTaken(durationSeconds);
//
//        return response;
//    }
//
//
//    public String generateIssueSummaryText(IssueResponse issueResponse) {
//        StringBuilder issueSummary = new StringBuilder();
//
//        // 1. Add issue title (summary)
//        String summary = issueResponse.getFields().getSummary();
//        issueSummary.append("Issue Summary: ").append(summary).append("\n\n");
//
//        // 2. Add issue description
//        String descriptionText = extractDescriptionText(issueResponse.getFields().getDescription());
//        issueSummary.append("Description: ").append(descriptionText).append("\n\n");
//
//        // 3. Add comments
//        List<CommentResponse> comments = issueResponse.getFields().getComment().getComments();
//        Set<CommentResponse> commentSet = new HashSet<>(comments);
//
//        int index = 1;
//        for (CommentResponse comment : commentSet) {
////            String commentText = String.valueOf(Description.extractTextFromDescription(comment.getBody()));
////            issueSummary.append("Comment: ").append(index++).append(":\n").append(commentText).append("\n");
//        }
//
//        // 4. Add first attachment (if any)
//        List<Attachment> attachments = issueResponse.getFields().getAttachments();
//        if (attachments != null && !attachments.isEmpty()) {
//            Attachment attachment = attachments.getFirst();
//            issueSummary.append("Attachment:\n")
//                    .append("Filename: ").append(attachment.getFilename()).append("\n")
//                    .append("URL: ").append(attachment.getContent()).append("\n");
//        }
//
//        System.out.println("IssueSummary: " + issueSummary);
//
//        return issueSummary.toString().trim();
//    }
//
//    public String extractDescriptionText(Description description) {
//        StringBuilder descriptionText = new StringBuilder();
//
//        if (description != null && description.getContent() != null) {
//            for (Description.Content content : description.getContent()) {
//                if (content.getContent() != null) {
//                    for (Description.Content.ContentItem node : content.getContent()) {
//                        if (node.getText() != null) {
//                            descriptionText.append(node.getText());
//                        }
//                    }
//                    descriptionText.append("\n"); // Optional: add a newline after each paragraph
//                }
//            }
//        }
//
//        return descriptionText.toString().trim();
//    }
//
//
//
//
//
//    private String extractPlainTextFromContent(List<Map<String, Object>> contentList) {
//        StringBuilder sb = new StringBuilder();
//
//        for (Map<String, Object> block : contentList) {
//            if ("paragraph".equals(block.get("type"))) {
//                List<Map<String, Object>> paragraphContent;
//                paragraphContent = (List<Map<String, Object>>) block.get("content");
//                for (Map<String, Object> textNode : paragraphContent) {
//                    if ("text".equals(textNode.get("type")) && textNode.get("text") != null) {
//                        sb.append(textNode.get("text"));
//                    }
//                }
//                sb.append("\n"); // separate paragraphs
//            }
//        }
//
//        return sb.toString().trim();
//    }
//
//
//    public List<String> getTimeEstimatesForIssue( String issueKey) {
//        GeminiResponse geminiResponse =  generateAnalysisForIssue(issueKey);
//
//        List<String> stringTextList = geminiResponse.getCandidates()
//                .stream()
//                .flatMap(candidate -> candidate.getContent().getParts().stream())
//                .map(Part::getText)
//                .toList(); //
//
//        System.out.println("\n\nallTexts: " + stringTextList);
//
//        return stringTextList;
//    }
//
//
//
//    public  List<TimeEstimate> extractTimeEstimates(String geminiText) {
//        List<TimeEstimate> estimates = new ArrayList<>();
//
//        // Match lines like "*   Initial Triage & Information Gathering: 0.5 - 1 hour"
//        Pattern pattern = Pattern.compile("\\*\\s+(.*?)\\s*:\\s*(\\d+(?:\\.\\d+)?\\s*-\\s*\\d+(?:\\.\\d+)?\\s*hour[s]?)");
//
//        Matcher matcher = pattern.matcher(geminiText);
//
//        while (matcher.find()) {
//            String task = matcher.group(1).trim();
//            String estimate = matcher.group(2).trim();
//            estimates.add(new TimeEstimate(task, estimate));
//        }
//
//        return estimates;
//    }
//
//
//
//
//    private String buildGeminiPrompt(Issue issue, CommentListResponse comments) {
//        StringBuilder prompt = new StringBuilder();
//
//        prompt.append("📌 Issue Title: ").append(issue.getSummary()).append("\n");
//        prompt.append("📝 Description: ").append(issue.getDescription()).append("\n\n");
//
//        prompt.append("💬 Comments:\n");
//        for (CommentResponse comment : comments.getComments()) {
//            prompt.append("- ").append(comment.getAuthor().getDisplayName())
//                    .append(": ").append(comment.getBody()).append("\n");
//        }
//
//        prompt.append("\n---\nPlease analyze the issue and suggest root cause, solution, and estimate time.");
//
//        return prompt.toString();
//    }
//
//
//
//
////    public GeminiResponse generateContent(String promptText) {
////        // Build the request payload for a single user text message
////        Body.ContentItem textPart = new Part(promptText);
////        Content userContent = new Content();
////        userContent.setContent(Collections.singletonList(textPart));
////
////        GeminiRequest request = new GeminiRequest();
////        request.setContents(Collections.singletonList(userContent));
////
////        // Optional: Add default generation config if not provided
////        // This can be overridden by more specific methods or passed as a parameter
////        GenerationConfig config = new GenerationConfig();
////        config.setTemperature(0.7);
////        config.setMaxOutputTokens(500);
////        request.setGenerationConfig(config);
////
////        return executeGeminiApiCall(request, GENERATE_CONTENT_API_URL);
////    }
////
////
////    public GeminiResponse chat(List<Content> contents, GenerationConfig generationConfig) {
////        if (contents == null || contents.isEmpty()) {
////            System.err.println("Chat contents cannot be null or empty.");
////            return null;
////        }
////
////        GeminiRequest request = new GeminiRequest();
////        request.setContents(contents); // Set the full conversation history
////
////        if (generationConfig != null) {
////            request.setGenerationConfig(generationConfig);
////        } else {
////            // Provide a default if no config is supplied for chat
////            GenerationConfig defaultConfig = new GenerationConfig();
////            defaultConfig.setTemperature(0.7);
////            defaultConfig.setMaxOutputTokens(500);
////            request.setGenerationConfig(defaultConfig);
////        }
////
////        return executeGeminiApiCall(request, GENERATE_CONTENT_API_URL);
////    }
////
////
////    public GeminiResponse generateMultimodalContent(String promptText, String base64ImageData, String mimeType, GenerationConfig generationConfig) {
////        if (promptText == null || promptText.isEmpty() || base64ImageData == null || base64ImageData.isEmpty() || mimeType == null || mimeType.isEmpty()) {
////            System.err.println("Multimodal content requires text, image data, and mime type.");
////            return null;
////        }
////
////        Part textPart = new Part(promptText);
////        InlineData imageData = new InlineData(mimeType, base64ImageData);
////        Part imagePart = new Part(imageData);
////
////        Content userContent;
////        userContent = new Content("user", Arrays.asList(textPart, imagePart));
////
////        GeminiRequest request = new GeminiRequest();
////        request.setContents(Collections.singletonList(userContent));
////
////        if (generationConfig != null) {
////            request.setGenerationConfig(generationConfig);
////        } else {
////            GenerationConfig defaultConfig = new GenerationConfig();
////            defaultConfig.setTemperature(0.4);
////            defaultConfig.setMaxOutputTokens(300);
////            request.setGenerationConfig(defaultConfig);
////        }
////
////        return executeGeminiApiCall(request, GENERATE_CONTENT_API_URL);
////    }
//
//
////    private GeminiResponse executeGeminiApiCall(GeminiRequest request, String apiUrl) {
////
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////
////        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);
////
////        String fullUrl = apiUrl + "?key=" + geminiProperties.getKey();
////
////        try {
////            return restTemplate.postForObject(fullUrl, entity, GeminiResponse.class);
////        } catch (Exception e) {
////            System.err.println("Error calling Gemini API at " + fullUrl + ": " + e.getMessage());
////            e.printStackTrace();
////            return null;
////        }
////    }
//
//
//}
//
