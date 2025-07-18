package br.imd.mybookplace.DTOS;

import br.imd.framework.DTOs.LLMRequestDTO;

public class BookLLMRequestDTO extends LLMRequestDTO{

    public BookLLMRequestDTO(String prompt) {
        super(prompt);
    }

    public String getPrompt() {
        return super.getPrompt();
    }

}
