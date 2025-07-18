package br.imd.mybookplace.DTOS;

import br.imd.framework.DTOs.LLMRequestDTO;

public class BookLLMRequestDTO extends LLMRequestDTO{

    public BookLLMRequestDTO(String prompt) {
        super(prompt);
    }

    public String getPrompt() {
        String promptConfiguration = "You are a shopping assistant who specializes in finding the best deals on books."+
    "Your task is to parse the given prompt and return a list of relevant deals. Each deal should contain the book"+ 
    "title, price, purchase link, and image URL for the book. You should return a list of JSON objects,"+ 
    "where each object represents a deal. Organize them as follows: title, author, price, link, imageUrl. prompt: " + super.getPrompt();
        return promptConfiguration;
    }

}
