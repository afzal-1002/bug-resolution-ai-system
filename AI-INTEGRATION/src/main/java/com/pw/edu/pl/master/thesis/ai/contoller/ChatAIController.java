package com.pw.edu.pl.master.thesis.ai.contoller;

import com.pw.edu.pl.master.thesis.ai.dto.ai.ChatMessage;
import com.pw.edu.pl.master.thesis.ai.service.ChatAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ChatAIController {

    private final ChatAIService chatAIService;

    // non-streaming (one shot)
    @GetMapping("/ai/pirates")
    public ChatResponse pirates() {
        return chatAIService.generatePirates();
    }

    // STREAMING
    @GetMapping(value = "/ai/pirates/stream")
    public Flux<ChatResponse> piratesStream() {
        return chatAIService.streamPirates();
    }

    @GetMapping(value = "/ai/stream/response")
    public Flux<String> streamResponse(@RequestParam(value = "question", required = false) String question) {
        return chatAIService.streamResponse(question);
    }
}
