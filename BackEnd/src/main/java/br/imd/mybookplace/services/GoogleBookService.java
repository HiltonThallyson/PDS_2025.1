package br.imd.mybookplace.services;

import br.imd.mybookplace.DTOS.LivroDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GoogleBookService {
   private final WebClient webClient;

    public GoogleBookService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://www.googleapis.com/books/v1").build();
    }

    public List<LivroDTO> buscarLivrosPorTitulo(String titulo) {
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
            return Collections.emptyList(); // Retorna lista vazia se não houver resultado
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
        List<LivroDTO> livros = new ArrayList<>();

        for (Map<String, Object> item : items) {
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

            livros.add(dto);
        }

        return livros;
    }

    public List<LivroDTO> buscarLivrosPorAutor(String autor) {
        // Monta a URL da consulta com o parâmetro de autor
        String url = UriComponentsBuilder.fromPath("/volumes")
                .queryParam("q", "inauthor:" + autor)
                .build().toString();

        // Faz a requisição à API do Google Books
        Map<String, Object> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Map.class)
                .block(); // .block() para obter resultado síncrono

        if (response == null || response.get("items") == null) {
            return Collections.emptyList(); // Retorna lista vazia se não houver resultado
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");
        List<LivroDTO> livros = new ArrayList<>();

        for (Map<String, Object> item : items) {
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

            livros.add(dto);
        }

        return livros;
    }


}
