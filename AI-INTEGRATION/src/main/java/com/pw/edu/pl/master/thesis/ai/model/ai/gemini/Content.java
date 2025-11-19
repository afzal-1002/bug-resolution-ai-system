package com.pw.edu.pl.master.thesis.ai.model.ai.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data @NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String role;
    private List<Part> parts;
}
