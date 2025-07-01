package br.imd.mybookplace.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.imd.framework.DTOs.OfferDTO;
import br.imd.framework.controllers.BaseLLMController;
import br.imd.framework.exceptions.LLMServiceException;
import br.imd.mybookplace.DTOS.LLMRequestDTOLivro;
import br.imd.mybookplace.services.LLMServiceLivro;


@RestController
@RequestMapping("/api/livros/llm")
public class LivroLLMController extends BaseLLMController <LLMRequestDTOLivro, LLMServiceLivro> {

    public LivroLLMController(LLMServiceLivro llmService) {
        super(llmService);
    }

    @PostMapping("/search_price")

    public List<OfferDTO> searchOffers(
    @RequestHeader("Authorization") String authorizationHeader,
     @RequestBody LLMRequestDTOLivro prompt) throws JsonMappingException, JsonProcessingException {
            return llmService.makeSearchPricePrediction(prompt);
    }

    @PostMapping("/generate_image_by_text")
    public ResponseEntity<?> generateImage( 
        @RequestHeader("Authorization") String authorizationHeader,
        @RequestBody String prompt) {

        try {
            byte[] imageBytes = llmService.createImage(prompt);
            return ResponseEntity.ok()
                .header("Content-Type", "image/png") // Certifique-se que a API Python retorna PNG
                .body(imageBytes);
        } catch (LLMServiceException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao gerar a imagem: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Ocorreu um erro inesperado no servidor.");
        }
    }

    
    
}
