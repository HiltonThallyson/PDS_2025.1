package br.imd.mymovieplace.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.imd.framework.services.LLMService;
import br.imd.mymovieplace.DTOS.MovieLLMRequestDTO;

@Service
public class LLMServiceFilme extends LLMService<MovieLLMRequestDTO> {
   public LLMServiceFilme(WebClient.Builder builder) {
        super(builder);
    }
}
