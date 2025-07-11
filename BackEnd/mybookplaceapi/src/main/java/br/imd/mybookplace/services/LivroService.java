package br.imd.mybookplace.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import br.imd.framework.exceptions.GoogleBooksApiException;
import br.imd.framework.services.ProdutoService;
import br.imd.mybookplace.DTOS.LivroDTO;

@Service
public class LivroService extends ProdutoService<LivroDTO>{

    final String API_URL = "https://www.googleapis.com/books/v1";
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    public LivroService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }

    @Override
    public List<LivroDTO> buscarPorNome(String titulo) {
        String url = construirUrl("intitle", titulo, null);
        return buscarLivroDTOs(url);
    }

    @Override
    public List<LivroDTO> buscarPorQuantidade(int qtdPorCategoria) {
        String [] categorias = {"history", "science", "technology", "art", "fiction"};
        List<LivroDTO> LivroDTOs = new ArrayList<>();

        for (String categoria : categorias) {
            String url = construirUrl("subject", categoria, qtdPorCategoria);

            LivroDTOs.addAll(buscarLivroDTOs(url));
        }

        return LivroDTOs;
    }

     private String construirUrl(String tipoConsulta, String valorConsulta, Integer maxResults) {
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromPath("/volumes")
            .queryParam("q", tipoConsulta + ":" + valorConsulta);

        if (maxResults != null) {
            builder.queryParam("maxResults", maxResults);
        }

        return builder.build().toString();
    }

   private List<LivroDTO> buscarLivroDTOs(String url){
        Map<String, Object> response = fazerRequisicaoAPIGoogle(url);

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

        List<LivroDTO> LivroDTOs = converterParaLivroDTOs(items);
        
        return LivroDTOs;

    }

    private Map<String, Object> fazerRequisicaoAPIGoogle(String url){
        try{
            Map<String, Object> response = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(Map.class)
            .block(); // .block() para obter resultado síncrono

            if (response == null || response.get("items") == null) {
                return Collections.emptyMap(); // Retorna lista vazia se não houver resultado
            }
    
            return response;
        } catch (WebClientResponseException | WebClientRequestException e) {
            logger.error("Erro ao acessar a API do Google Books: {}", e.getMessage());
            throw new GoogleBooksApiException("Erro ao acessar a API do Google Books:" + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erro inesperado na API do Google Books: {}", e.getMessage());
            throw new GoogleBooksApiException("Erro inesperado: " + e.getMessage(), e);
        }
    }

        private List<LivroDTO> converterParaLivroDTOs(List<Map<String, Object>> items){
        List<LivroDTO> LivroDTOs = new ArrayList<>();

        for(Map<String, Object> item: items){
            Map<String, Object> volumeInfo = (Map<String, Object>) item.get("volumeInfo");

            LivroDTO dto = new LivroDTO();
            dto.setTitle((String) volumeInfo.get("title"));
            dto.setSubtitle((String) volumeInfo.get("subtitle"));
            dto.setAuthors((List<String>) volumeInfo.get("authors"));
            dto.setEditora((String) volumeInfo.get("publisher"));
            dto.setDescription((String) volumeInfo.get("description"));
            dto.setCategories((List<String>) volumeInfo.get("categories"));

            Map<String, String> imageLinks = (Map<String, String>) volumeInfo.get("imageLinks");
            if (imageLinks != null) {
                dto.setThumbnail(imageLinks.get("thumbnail"));
            }

            // Extrair ISBN
            List<Map<String, String>> industryIdentifiers = (List<Map<String, String>>) volumeInfo.get("industryIdentifiers");
            if (industryIdentifiers != null) {
                String isbn13 = null;
                String isbn10 = null;
                for (Map<String, String> identifierMap : industryIdentifiers) {
                    if (identifierMap != null) {
                        String type = identifierMap.get("type");
                        String idValue = identifierMap.get("identifier");
                        if ("ISBN_13".equals(type)) {
                            isbn13 = idValue;
                        } else if ("ISBN_10".equals(type)) {
                            isbn10 = idValue;
                        }
                    }
                }
                // Prioriza ISBN_13, mas usa ISBN_10 se o primeiro não estiver disponível
                dto.setIsbn(isbn13 != null ? isbn13 : isbn10);
            }

            LivroDTOs.add(dto);
        }
        return LivroDTOs;
    }
    
}
