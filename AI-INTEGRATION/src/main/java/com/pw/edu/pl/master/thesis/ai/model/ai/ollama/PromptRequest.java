package com.pw.edu.pl.master.thesis.ai.model.ai.ollama;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString @Builder
public class PromptRequest {
    private String prompt;
    private String model; // optional override
    public String getPrompt(){return prompt;}
    public void setPrompt(String p){this.prompt=p;}
    public String getModel(){return model;}
    public void setModel(String m){this.model=m;}
}