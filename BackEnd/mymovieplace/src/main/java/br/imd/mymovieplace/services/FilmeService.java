package br.imd.mymovieplace.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import br.imd.framework.exceptions.GoogleBooksApiException;
import br.imd.framework.services.ProdutoService;
import br.imd.mymovieplace.DTOS.FilmeDTO;

@Service
public class FilmeService extends ProdutoService<FilmeDTO>{

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(FilmeService.class);
    final String API_URL = "https://api.themoviedb.org/3";
    
   @Value("${tmdb.api.token}")
    private String tmdbToken;

    public FilmeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }

    @Override
    public List<FilmeDTO> buscarPorNome(String titulo) {
        String url = "/movie/popular?name=" + titulo;
        List<FilmeDTO> todosFilmes = buscarFilmeDTOs(url);

        return todosFilmes.stream()
                .filter(Filme -> Filme.getTitle() != null && Filme.getTitle().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    @Override
    public List<FilmeDTO> buscarPorQuantidade(int qtdPorCategoria) {
        String url = "/movie/popular?language=pt-BR";
        
                        
        List<FilmeDTO> todosFilmes = buscarFilmeDTOs(url);
        return todosFilmes.stream().limit(qtdPorCategoria).toList();
    }

    private Map<Integer, String> obterGeneros() {
        String genresUrl = "/genre/movie/list";
        try {
            Map<String, Object> response = webClient.get()
                .uri(genresUrl)
                .header("Authorization", "Bearer " + tmdbToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

            List<Map<String, Object>> genres = (List<Map<String, Object>>) response.get("genres");
            Map<Integer, String> mapa = new HashMap<>();

            for (Map<String, Object> genre : genres) {
                Integer id = (Integer) genre.get("id");
                String name = (String) genre.get("name");
                mapa.put(id, name);
            }

            return mapa;

        } catch (Exception e) {
            logger.error("Erro ao obter lista de gêneros: {}", e.getMessage());
            return Collections.emptyMap();
        }
    }


    private List<FilmeDTO> buscarFilmeDTOs(String url){
        List<Map<String, Object>> items = fazerRequisicaoTMDB(url);
        return converterParaFilmeDTOs(items);
    }

    private List<Map<String, Object>> fazerRequisicaoTMDB(String urlPath){
        try {
            Map<String, Object> response = webClient.get()
                .uri(urlPath)
                .header("Authorization", "Bearer " + tmdbToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

            if (response == null || !response.containsKey("results")) {
                return Collections.emptyList();
            }

            return (List<Map<String, Object>>) response.get("results");

        } catch (WebClientResponseException | WebClientRequestException e) {
            logger.error("Erro ao acessar a API: ", e.getMessage());
            throw new GoogleBooksApiException("Erro ao acessar a API TMDB: " + e.getMessage(), e);
        }
    }


    private List<FilmeDTO> converterParaFilmeDTOs(List<Map<String, Object>> items) {
        Map<Integer, String> generos = obterGeneros();
        List<FilmeDTO> filmeDTOs = new ArrayList<>();

        for (Map<String, Object> item : items) {
            FilmeDTO dto = new FilmeDTO();
            dto.setTitle((String) item.get("title"));
            dto.setSubtitle((String) item.get("original_title"));
            dto.setDescription((String) item.get("overview"));
            dto.setThumbnail("https://image.tmdb.org/t/p/w500" + item.get("poster_path"));

            List<Integer> genreIds = (List<Integer>) item.get("genre_ids");
            List<String> genreNames = genreIds != null
                ? genreIds.stream()
                        .map(generos::get)
                        .filter(Objects::nonNull)
                        .toList()
                : Collections.emptyList();

            dto.setCategories(genreNames);

            filmeDTOs.add(dto);
        }

        return filmeDTOs;
    }

}
