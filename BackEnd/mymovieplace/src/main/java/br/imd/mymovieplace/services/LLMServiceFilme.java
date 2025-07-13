package br.imd.mymovieplace.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.imd.framework.DTOs.LLMRequestDTO;
import br.imd.framework.DTOs.OfferDTO;
import br.imd.framework.exceptions.LLMServiceException;
import br.imd.framework.services.LLMService;

@Service
public class LLMServiceFilme extends LLMService<LLMRequestDTO> {
    private  WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    final int bufferSizeInBytes = 20 * 1024 * 1024; // Aumentado para 20 MB como exemplo

    public LLMServiceFilme(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://127.0.0.1:8000/api").build();
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(bufferSizeInBytes))
                .build();

        webClient = builder
                .baseUrl("http://127.0.0.1:8000/api") 
                .exchangeStrategies(strategies)       
                .build();
    }

    /**
     * Busca ofertas de Jogos a partir do prompt fornecido, utilizando a API local de LLM.
     *
     * @param prompt Objeto contendo as informações para busca de ofertas.
     * @return Lista de ofertas encontradas.
     * @throws LLMServiceException em caso de falha na comunicação ou processamento da resposta da API.
     */
    public String searchOffers(LLMRequestDTO prompt) {

        String promptConfiguration = "You are a shopping assistant who specializes in finding the best deals or places to watch movies."+
    "Your task is to parse the given prompt and return a list of relevant deals or where to watch. Each deal should contain the movie"+ 
    "title, price, link, and imageURL for the movie. You should return a list of JSON objects,"+ 
    "where each object represents a deal. Organize them as follows: title, price, link, imageUrl. prompt: ";

        LLMRequestDTO myCustomPrompt = new LLMRequestDTO(promptConfiguration + prompt.getPrompt());
        String searchPriceUrl = UriComponentsBuilder
                .fromPath("/search_price")
                .build()
                .toString();
        try {
            System.out.println(myCustomPrompt.getPrompt());
            String rawJson = webClient.post()
                    .uri(searchPriceUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(myCustomPrompt)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            System.out.println("Resposta da API: " + rawJson);
                    return rawJson;

        } catch (Exception e) {
            throw new LLMServiceException("Falha ao buscar ofertas da API externa: " + e.getMessage(), e);
        }
    }

    /**
     * Gera uma imagem a partir do prompt fornecido, utilizando a API local de LLM.
     *
     * @param prompt Objeto contendo as informações para geração da imagem.
     * @return String com a URL ou base64 da imagem gerada.
     * @throws LLMServiceException em caso de falha na comunicação ou processamento da resposta da API.
     */
    public byte[] createImage(String prompt) {
        String imageUrlEndpoint = UriComponentsBuilder 
                .fromPath("/generate-image-from-text")
                .build()
                .toString();

                String promptConfiguration = "Generate an image based on the following description of a movie: ";

        LLMRequestDTO myCustomPrompt = new LLMRequestDTO(promptConfiguration + prompt);
        System.out.println("Prompt para geração de imagem: " + myCustomPrompt.getPrompt());

        try {
            return webClient.post()
                    .uri(imageUrlEndpoint)
                    .accept(MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG, MediaType.APPLICATION_OCTET_STREAM)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(myCustomPrompt)
                    .retrieve()
                    .bodyToMono(byte[].class)
                    .block(); 
        } catch (Exception e) {
            e.printStackTrace(); 
            throw new LLMServiceException("Falha ao gerar imagem da API externa: " + e.getMessage(), e);
        }
    }

    @Override
    public List<OfferDTO> extractSearchPriceResponse(String response) throws JsonMappingException, JsonProcessingException {
        List<Map<String, Object>> parsed = objectMapper.readValue(response, new TypeReference<>() {
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
    }
}
