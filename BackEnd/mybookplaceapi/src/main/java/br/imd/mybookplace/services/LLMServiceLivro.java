package br.imd.mybookplace.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.imd.framework.DTOs.LLMRequestDTO;
import br.imd.framework.DTOs.OfferDTO;
import br.imd.framework.exceptions.LLMException;
import br.imd.framework.services.LLMService;
import br.imd.mybookplace.DTOS.BookLLMRequestDTO;

@Service
public class LLMServiceLivro extends LLMService<BookLLMRequestDTO> {

    public LLMServiceLivro(WebClient.Builder builder) {
        super(builder);
    }
}
