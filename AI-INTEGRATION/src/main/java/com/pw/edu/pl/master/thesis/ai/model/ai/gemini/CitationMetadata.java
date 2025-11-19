package com.pw.edu.pl.master.thesis.ai.model.ai.gemini;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor
@AllArgsConstructor
public class CitationMetadata {
    @JsonProperty("citations")
    private List<Citation> citations;
}
