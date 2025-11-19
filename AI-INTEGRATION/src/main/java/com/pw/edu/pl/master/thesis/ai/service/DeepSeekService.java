package com.pw.edu.pl.master.thesis.ai.service;

import com.pw.edu.pl.master.thesis.ai.model.ai.deepseek.DeepSeekChatRequest;
import com.pw.edu.pl.master.thesis.ai.model.ai.deepseek.DeepSeekChatResponse;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.StreamingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.stereotype.Service;

@Service
public interface DeepSeekService  {
    DeepSeekChatResponse chat(DeepSeekChatRequest request);
}
