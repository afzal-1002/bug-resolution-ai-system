package com.pw.edu.pl.master.thesis.ai.model.ai.gemini;

//@RestController
//@RequestMapping("/api/gemini") // Base path for all Gemini-related endpoints
//@RequiredArgsConstructor
//public class GeminiController {
//
//    private final GeminiService geminiService;
//    private final  GeminiClient geminiClient;

//    @PostMapping("/generate-text")
//    public ResponseEntity<GeminiResponse> generateText(@RequestBody GenerateTextRequest request) {
//        if (request == null || request.getPromptText() == null || request.getPromptText().isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        GeminiResponse response = geminiService.generateContent(request.getPromptText());
//
//        if (response != null) {
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            // Handle cases where the service returns null (e.g., API error)
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//

//    @PostMapping("/chat")
//    public ResponseEntity<GeminiResponse> chat(@RequestBody ChatRequest request) {
//        if (request == null || request.getContents() == null || request.getContents().isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        GeminiResponse response = geminiService.chat(request.getContents(), request.getGenerationConfig());
//
//        if (response != null) {
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    @PostMapping(value = "/generate-multimodal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<GeminiResponse> generateMultimodal(
//            @RequestPart("request") GenerateMultimodalRequest request,
//            @RequestPart("imageFile") MultipartFile imageFile) {
//
//        if (request == null || request.getPromptText() == null || request.getPromptText().isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        if (imageFile == null || imageFile.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // Convert image to Base64
//            byte[] imageBytes = imageFile.getBytes();
//            String base64ImageData = ImageEncoderUtil.encodeImageToBase64(imageBytes);
//            String mimeType = imageFile.getContentType(); // Get MIME type from the uploaded file
//
//            if (base64ImageData == null || mimeType == null) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Could not encode or get mime type
//            }
//
//            GeminiResponse response = geminiService.generateMultimodalContent(
//                    request.getPromptText(),
//                    base64ImageData,
//                    mimeType,
//                    request.getGenerationConfig()
//            );
//
//            if (response != null) {
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } catch (IOException e) {
//            System.err.println("Error processing uploaded image: " + e.getMessage());
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    @PostMapping("/ai-analysis/{issueKey}")
//    public ResponseEntity<GeminiResponse> analyzeIssueWithGemini(@PathVariable String issueKey) {
////        GeminiResponse response = geminiService.generateAnalysisForIssue(issueKey);
////        return ResponseEntity.ok(response);
//    }


//        @PostMapping("/analysis/{issueKey}")
//        public ResponseEntity<List<String>> getTimeEstimatesForIssue (@PathVariable String issueKey){
//            List<String> estimateList = geminiService.getTimeEstimatesForIssue(issueKey);
//            return ResponseEntity.ok(estimateList);
//        }


//}
