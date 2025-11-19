package com.pw.edu.pl.master.thesis.ai.service.implementation;


import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import com.pw.edu.pl.master.thesis.ai.dto.ai.ChatMessage;
import com.pw.edu.pl.master.thesis.ai.service.GeminiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class GeminiServiceImplementation implements GeminiService {

    private final Client client;

    public String getAiResponse(ChatMessage message) {
        GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash",
                message.getContent(),
                null
        );
        return response.text();
    }

}


