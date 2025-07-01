package br.imd.framework.DTOs;

public abstract class LLMRequestDTO {
    private String prompt;

    public LLMRequestDTO() {
    }

    public LLMRequestDTO(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}

