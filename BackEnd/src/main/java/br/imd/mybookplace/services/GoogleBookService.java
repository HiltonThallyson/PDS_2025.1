package br.imd.mybookplace.services;

import br.imd.mybookplace.DTOS.LivroDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class GoogleBookService {
   private final WebClient webClient;

    public GoogleBookService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://www.googleapis.com/books/v1").build();
    }

    public LivroDTO buscarLivroPorTitulo(String titulo){
        // Monta a URL da consulta com o parâmetro de título
        String url = UriComponentsBuilder.fromPath("/volumes")
                .queryParam("q", "intitle:" + titulo)
                .build().toString();

        // Faz a requisição à API do Google Books
        Map<String, Object> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .block(); // .block() para obter resultado síncrono

        if (response == null || response.get("items") == null) {
            return null;
        }

        // Pegamos o primeiro livro retornado
        Map<String, Object> primeiroItem = ((List<Map<String, Object>>) response.get("items")).get(0);
        Map<String, Object> volumeInfo = (Map<String, Object>) primeiroItem.get("volumeInfo");

        // Preenche o DTO com as informações relevantes
        LivroDTO dto = new LivroDTO();
        dto.setTitle((String) volumeInfo.get("title"));
        dto.setSubtitle((String) volumeInfo.get("subtitle"));
        dto.setAuthors((List<String>) volumeInfo.get("authors"));
        dto.setEditora((String) volumeInfo.get("publisher"));
       dto.setDescription((String) volumeInfo.get("description"));

        dto.setCategories((List<String>) volumeInfo.get("categories"));

        // Links
        Map<String, String> imageLinks = (Map<String, String>) volumeInfo.get("imageLinks");
        if (imageLinks != null) {
            dto.setThumbnail(imageLinks.get("thumbnail"));
        }
        System.out.println("cheguei aqui");
        System.out.println(dto);
        return dto;
    }



}
