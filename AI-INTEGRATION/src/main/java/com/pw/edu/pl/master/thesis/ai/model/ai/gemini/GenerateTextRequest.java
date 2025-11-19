package com.pw.edu.pl.master.thesis.ai.model.ai.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateTextRequest {
    private String promptText;
    // You could optionally add GenerationConfig here if you want to allow overriding defaults
    // private GenerationConfig generationConfig;
}
