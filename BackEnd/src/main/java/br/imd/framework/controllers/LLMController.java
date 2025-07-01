package br.imd.framework.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.imd.framework.DTOs.LLMRequestDTO;
import br.imd.framework.DTOs.OfferDTO;
import br.imd.framework.exceptions.LLMServiceException;
import br.imd.framework.services.LLMService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


/**
 * Controller responsável por expor endpoints de busca de ofertas e geração de imagens via LLM.
 */
@RestController
@RequestMapping("/api/llm")
public class LLMController {

    @Autowired
    private LLMService llmService;
    
    /**
     * Busca ofertas de livros a partir do prompt fornecido.
     *
     * @param prompt Objeto contendo as informações para busca de ofertas.
     * @return Lista de ofertas encontradas.
     * @throws JsonProcessingException 
     * @throws JsonMappingException 
     */
    @PostMapping("/search_price")

    public List<OfferDTO> searchOffers(
    @RequestHeader("Authorization") String authorizationHeader,
     @RequestBody LLMRequestDTO prompt) throws JsonMappingException, JsonProcessingException {
            return llmService.makeSearchPricePrediction(prompt);
    }

    /**
     * Gera uma imagem a partir do prompt fornecido.
     *
     * @param prompt Objeto contendo as informações para geração da imagem.
     * @return String com a URL ou base64 da imagem gerada.
     */
    // br.imd.mybookplace.controllers.LLMController

    @PostMapping("/generate_image_by_text")
    public ResponseEntity<?> generateImage( 
        @RequestHeader("Authorization") String authorizationHeader,
        @RequestBody LLMRequestDTO prompt) {

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
