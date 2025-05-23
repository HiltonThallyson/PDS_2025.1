package br.imd.mybookplace.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.imd.mybookplace.DTOS.LLMRequestDTO;
import br.imd.mybookplace.DTOS.OfferDTO;
import br.imd.mybookplace.exceptions.LLMServiceException;

@Service
public class LLMService {

    private  WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
       private static final Logger logger = LoggerFactory.getLogger(GoogleBookService.class);


    final int bufferSizeInBytes = 20 * 1024 * 1024; // Aumentado para 20 MB como exemplo

    public LLMService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://127.0.0.1:8000/api").build();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(bufferSizeInBytes))
                .build();

        webClient = builder
                .baseUrl("http://127.0.0.1:8000/api") 
                .exchangeStrategies(strategies)       
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
                        String priceString = (String) item.get("price");
                        priceString = priceString.replace("R$", "");
                        priceString = priceString.replace(",", ".");
                        price = Double.parseDouble(priceString);
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Erro ao converter preço");
                    }
                }

                if (title != null && link != null) {
                    offers.add(new OfferDTO(title, price, link, imageUrl));
                }
            }

            return offers;

        } catch (Exception e) {
            logger.error("Falha ao buscar ofertas da API externa", e);
            throw new LLMServiceException("Falha ao buscar ofertas da API externa: " + e.getMessage(), e);
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
                    .accept(MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG, MediaType.APPLICATION_OCTET_STREAM)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(prompt)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); 
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new LLMServiceException("Falha ao gerar imagem da API externa: " + e.getMessage(), e);
        }
    }
}
