package br.imd.mybookplace.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.imd.mybookplace.DTOS.LLMRequestDTO;
import br.imd.mybookplace.DTOS.OfferDTO;

@Service
public class LLMService {

    private  WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    final int bufferSizeInBytes = 20 * 1024 * 1024; // Aumentado para 20 MB como exemplo

        

    public LLMService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://127.0.0.1:8000/api").build();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(bufferSizeInBytes))
                .build();

        webClient = builder
                .baseUrl("http://127.0.0.1:8000/api") // Sua URL base da API FastAPI
                .exchangeStrategies(strategies)       // <<--- ESSA LINHA É CRUCIAL
                .build();
    }

    public List<OfferDTO> searchOffers(LLMRequestDTO prompt) {
        String searchPriceUrl = UriComponentsBuilder
                .fromPath("/search_price")
                .build()
                .toString();

        try {
            String rawJson = webClient.post()
                    .uri(searchPriceUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(prompt)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Tenta fazer o parse como uma lista de mapas
            List<Map<String, Object>> parsed = objectMapper.readValue(rawJson, new TypeReference<>() {
            });

            List<OfferDTO> offers = new ArrayList<>();

            for (Map<String, Object> item : parsed) {
                String title = item.get("title") != null ? item.get("title").toString() : null;
                String link = item.get("link") != null ? item.get("link").toString() : null;
                String imageUrl = item.get("imageUrl") != null ? item.get("imageUrl").toString() : null;
                Double price = null;

                if (item.get("price") instanceof Number) {
                    price = ((Number) item.get("price")).doubleValue();
                } else if (item.get("price") instanceof String) {
                    try {
                        price = Double.parseDouble((String) item.get("price"));
                    } catch (NumberFormatException e) {
                        // ignora valor inválido
                    }
                }

                if (title != null && link != null) {
                    offers.add(new OfferDTO(title, price, link, imageUrl));
                }
            }

            return offers;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String createImage(LLMRequestDTO prompt) {
        String imageUrlEndpoint = UriComponentsBuilder 
                .fromPath("/generate-image-from-text")
                .build()
                .toString();

        
        try {
            return webClient.post()
                    .uri(imageUrlEndpoint)
                    .accept(MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG, MediaType.APPLICATION_OCTET_STREAM) // ou MediaType.TEXT_PLAIN se a API retornar só o base64
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(prompt)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); 
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new RuntimeException("Falha ao gerar imagem da API externa: " + e.getMessage(), e);
        }
    }

    
}
