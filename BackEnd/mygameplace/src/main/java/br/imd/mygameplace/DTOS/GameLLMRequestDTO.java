package br.imd.mygameplace.DTOS;

import br.imd.framework.DTOs.LLMRequestDTO;

public class GameLLMRequestDTO extends LLMRequestDTO{

    public GameLLMRequestDTO(String prompt) {
        super(prompt);
    }

    public String getPrompt() {
        String promptConfiguration = "You are a shopping assistant who specializes in finding the best deals on games."+
    "Your task is to parse the given prompt and return a list of relevant deals. Each deal should contain the game"+ 
    "title, price, purchase link, and image URL for the game. You should return a list of JSON objects,"+ 
    "where each object represents a deal. Organize them as follows: title, author, price, link, imageUrl. prompt: " + super.getPrompt();
        return promptConfiguration;
    }

}
