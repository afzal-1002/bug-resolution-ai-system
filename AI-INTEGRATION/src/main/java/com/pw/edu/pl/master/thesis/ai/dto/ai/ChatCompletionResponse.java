package com.pw.edu.pl.master.thesis.ai.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class ChatCompletionResponse {
    private String id;
    private String model;
    private List<ChatChoice> choices;
    private ChatCompletionUsage usage;
}