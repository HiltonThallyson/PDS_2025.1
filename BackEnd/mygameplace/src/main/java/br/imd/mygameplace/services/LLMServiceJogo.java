package br.imd.mygameplace.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import br.imd.framework.services.LLMService;
import br.imd.mygameplace.DTOS.GameLLMRequestDTO;

@Service
public class LLMServiceJogo extends LLMService<GameLLMRequestDTO> {
    
    public LLMServiceJogo(WebClient.Builder builder) {
        super(builder);
    }
}
