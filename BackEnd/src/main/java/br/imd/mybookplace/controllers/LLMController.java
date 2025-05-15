package br.imd.mybookplace.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.imd.mybookplace.DTOS.LLMRequestDTO;
import br.imd.mybookplace.DTOS.OfferDTO;
import br.imd.mybookplace.services.LLMService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/llm")
public class LLMController {

    @Autowired
    private LLMService llmService;
    
    @PostMapping("/search_price")
    public List<OfferDTO> searchOffers(@RequestBody LLMRequestDTO prompt) {
        
        return llmService.searchOffers(prompt);
    }

    @PostMapping("/generate_image_by_text")
    public String postMethodName(@RequestBody LLMRequestDTO prompt) {
        
        
        return llmService.createImage(prompt);
    }
}
