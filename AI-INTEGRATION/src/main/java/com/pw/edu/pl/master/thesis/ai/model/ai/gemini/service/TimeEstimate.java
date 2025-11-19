package com.pw.edu.pl.master.thesis.ai.model.ai.gemini.service;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeEstimate {
    private String task;
    private String estimateRange;
}