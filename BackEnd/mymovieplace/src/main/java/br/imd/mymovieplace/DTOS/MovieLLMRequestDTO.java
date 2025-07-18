package br.imd.mymovieplace.DTOS;

import br.imd.framework.DTOs.LLMRequestDTO;

public class MovieLLMRequestDTO extends LLMRequestDTO{

    public MovieLLMRequestDTO(String prompt) {
        super(prompt);
    }

    public String getPrompt() {
        String promptConfiguration = "You are a shopping assistant who specializes in finding the best deals on movies."+
    "Your task is to parse the given prompt and return a list of relevant deals. Each deal should contain the movie"+ 
    "title, price, purchase link, and image URL for the movie. You should return a list of JSON objects,"+ 
    "where each object represents a deal. Organize them as follows: title, director, price, link, imageUrl. prompt: " + super.getPrompt();
        return promptConfiguration;
    }

}