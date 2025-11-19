package com.pw.edu.pl.master.thesis.ai.model.ai.gemini;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor
@AllArgsConstructor
public class SafetySetting {
    private String category;
    private String threshold;

}

