package com.pw.edu.pl.master.thesis.ai.service.implementation;


import com.pw.edu.pl.master.thesis.ai.configuration.DeepSeekClientConfiguration;
import com.pw.edu.pl.master.thesis.ai.model.ai.deepseek.DeepSeekChatRequest;
import com.pw.edu.pl.master.thesis.ai.model.ai.deepseek.DeepSeekChatResponse;
import com.pw.edu.pl.master.thesis.ai.service.DeepSeekService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class DeepSeekServiceImplementation implements DeepSeekService {

    private final DeepSeekClientConfiguration deepSeekClientConfiguration;

    @Override
    public DeepSeekChatResponse chat(DeepSeekChatRequest request) {
        return deepSeekClientConfiguration.chat(request);
    }


    public Flux<ChatResponse> stream(@NotNull Prompt prompt) {
        return null;
    }
}
