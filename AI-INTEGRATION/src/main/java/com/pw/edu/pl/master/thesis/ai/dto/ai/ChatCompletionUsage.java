package com.pw.edu.pl.master.thesis.ai.dto.ai;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatCompletionUsage {
    private int promptTokens;
    private int completionTokens;
    private int totalTokens;

}