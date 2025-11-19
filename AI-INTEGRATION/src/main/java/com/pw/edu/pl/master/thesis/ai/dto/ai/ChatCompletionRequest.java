package com.pw.edu.pl.master.thesis.ai.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data  @Builder
@AllArgsConstructor  @NoArgsConstructor
public class ChatCompletionRequest {
    private String model;
    private List<ChatMessage> messages;

}
