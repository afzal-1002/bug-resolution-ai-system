package com.pw.edu.pl.master.thesis.ai.model.ai.gemini;

import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ConversationService {

    private final Map<String, List<GeminiChat>> sessions = new ConcurrentHashMap<>();

    public String startSession(String systemPrompt) {
        String sessionId = UUID.randomUUID().toString();
        GeminiChat systemTurn = new GeminiChat( null, "system", systemPrompt, OffsetDateTime.now());
        sessions.put(sessionId, new ArrayList<>(List.of(systemTurn)));
        return sessionId;
    }


    public void append(String sessionId, GeminiChat chat) {
        sessions.computeIfAbsent(sessionId, id -> new ArrayList<>())
                .add(chat);
    }


    public List<GeminiChat> getHistory(String sessionId) {
        return Collections.unmodifiableList(
                sessions.getOrDefault(sessionId, Collections.emptyList())
        );
    }


    public void clearSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
