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
     */
    @PostMapping("/search_price")

    public List<OfferDTO> searchOffers(
    @RequestHeader("Authorization") String authorizationHeader,
     @RequestBody LLMRequestDTO prompt) {
            return llmService.searchOffers(prompt);
    }

    /**
     * Gera uma imagem a partir do prompt fornecido.
     *
     * @param prompt Objeto contendo as informações para geração da imagem.
     * @return String com a URL ou base64 da imagem gerada.
     */
    @PostMapping("/generate_image_by_text")

    public String postMethodName(
    @RequestHeader("Authorization") String authorizationHeader, 
    @RequestBody LLMRequestDTO prompt) {

            return llmService.createImage(prompt);

    }
}
