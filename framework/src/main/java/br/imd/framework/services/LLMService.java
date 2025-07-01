package br.imd.framework.services;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.imd.framework.DTOs.LLMRequestDTO;
import br.imd.framework.DTOs.OfferDTO;

/**
 * Serviço responsável por consumir a API local de LLM para busca de ofertas e geração de imagens.
 */
public abstract class LLMService <T extends LLMRequestDTO> {

    public List<OfferDTO> makeSearchPricePrediction(T prompt) throws JsonMappingException, JsonProcessingException{
        String response = searchOffers(prompt);
        return extractSearchPriceResponse(response);
    }

    public abstract String searchOffers(T prompt);

    public abstract byte[] createImage(String prompt);

    public abstract List<OfferDTO> extractSearchPriceResponse(String response) throws JsonMappingException, JsonProcessingException;
        
}