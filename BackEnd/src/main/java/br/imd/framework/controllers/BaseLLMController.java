package br.imd.framework.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.imd.framework.DTOs.LLMRequestDTO;
import br.imd.framework.DTOs.OfferDTO;
import br.imd.framework.exceptions.LLMServiceException;
import br.imd.framework.services.LLMService;

public class BaseLLMController <T extends LLMRequestDTO, S extends LLMService<T>> {

    protected final S llmService;

    public BaseLLMController(S llmService) {
        this.llmService = llmService;
    }

    protected ResponseEntity<?> searchOffers(T prompt) {
        try {
            List<OfferDTO> offers = llmService.makeSearchPricePrediction(prompt);
            return ResponseEntity.ok(offers);
        } catch (JsonProcessingException | LLMServiceException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar a busca de ofertas: " + e.getMessage());
        }
    }

    
    protected ResponseEntity<byte[]> generateImages(String prompt) {
        try {
            byte[] imageBytes = llmService.createImage(prompt);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);
        } catch (LLMServiceException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
