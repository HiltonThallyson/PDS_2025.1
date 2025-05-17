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
        String url = construirUrl("intitle", titulo, null);
        return buscarLivros(url);
    }

    public List<LivroDTO> buscarLivrosPorAutor(String autor) {
        String url = construirUrl("inauthor", autor, null);

        return buscarLivros(url);
    }

    public List<LivroDTO> buscarLivrosPorQuantidade(int qtdPorCategoria) {
        String [] categorias = {"history", "science", "technology", "art", "fiction"};
        List<LivroDTO> livros = new ArrayList<>();

        for (String categoria : categorias) {
            String url = construirUrl("subject", categoria, qtdPorCategoria);

            livros.addAll(buscarLivros(url));
        }

        return livros;
    }

    public List<LivroDTO> buscarLivrosPorCategoria(String categoria){
        String url = construirUrl("subject", categoria, null);
   
        return buscarLivros(url);
    }

    public List<LivroDTO> buscarLivrosPorISBN(String isbn) {
        String url = construirUrl("isbn", isbn, 1);
        return buscarLivros(url);
    }

    private Map<String, Object> fazerRequisicaoAPIGoogle(String url){
        Map<String, Object> response = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(Map.class)
            .block(); // .block() para obter resultado síncrono
        
        if (response == null || response.get("items") == null) {
            return Collections.emptyMap(); // Retorna lista vazia se não houver resultado
        }

        return response;
    }

    private List<LivroDTO> converterParaLivros(List<Map<String, Object>> items){
        List<LivroDTO> livros = new ArrayList<>();

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

            livros.add(dto);
        }
        return livros;
    }

    private List<LivroDTO> buscarLivros(String url){
        Map<String, Object> response = fazerRequisicaoAPIGoogle(url);

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

        List<LivroDTO> livros = converterParaLivros(items);
        
        return livros;

        //return converterParaLivros((List<Map<String, Object>>) fazerRequisicaoAPIGoogle(url).get("items"));
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
}

