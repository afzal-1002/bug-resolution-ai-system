package com.pw.edu.pl.master.thesis.ai.service;

import com.pw.edu.pl.master.thesis.ai.dto.ai.ChatMessage;
import org.springframework.stereotype.Service;

@Service
public interface GeminiService {
    String getAiResponse(ChatMessage message);
}

