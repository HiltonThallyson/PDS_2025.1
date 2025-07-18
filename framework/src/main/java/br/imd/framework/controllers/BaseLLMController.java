package br.imd.framework.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.imd.framework.DTOs.LLMRequestDTO;
import br.imd.framework.DTOs.OfferDTO;
import br.imd.framework.exceptions.LLMException;
import br.imd.framework.services.LLMService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping("/api/llm")
public abstract class BaseLLMController <L extends LLMRequestDTO,S extends LLMService<L>> {

    protected final S llmService;

    public BaseLLMController(S llmService) {
        this.llmService = llmService;
    }

    @PostMapping("/search_price")
    protected ResponseEntity<?> searchOffers(@RequestHeader("Authorization") String authorizationHeader, @RequestBody L prompt)  throws JsonMappingException, JsonProcessingException{
        System.out.println(prompt.getPrompt());
        try {
            List<OfferDTO> offers = llmService.makeSearchPricePrediction(prompt);
            return ResponseEntity.ok(offers);
        } catch (JsonProcessingException | LLMException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a busca de ofertas: " + e.getMessage());
        }
    }

    @PostMapping("/generate_image_by_text")
    protected ResponseEntity<byte[]> generateImages(@RequestHeader("Authorization") String authorizationHeader, @RequestBody L prompt){
        try {
            byte[] imageBytes = llmService.createImage(prompt);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (LLMException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
