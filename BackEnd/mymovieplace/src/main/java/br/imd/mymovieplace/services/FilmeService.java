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

import br.imd.framework.exceptions.ApiException;
import br.imd.framework.services.ProdutoService;
import br.imd.mymovieplace.DTOS.FilmeDTO;

@Service
public class FilmeService extends ProdutoService<FilmeDTO> {

    private static final String API_URL = "https://api.themoviedb.org/3";
    private static final Logger logger = LoggerFactory.getLogger(FilmeService.class);

    @Value("${tmdb.api.token}")
    private String tmdbToken;

    public FilmeService(WebClient.Builder builder) {
        super(builder, API_URL, logger);
    }

    @Override
    public List<FilmeDTO> buscarPorNome(String titulo) {
        return buscarComConversao("/movie/popular?name=" + titulo).stream()
            .filter(f -> f.getTitle() != null && f.getTitle().toLowerCase().contains(titulo.toLowerCase()))
            .toList();
    }

    @Override
    public List<FilmeDTO> buscarPorQuantidade(int qtd) {
        return buscarComConversao("/movie/popular?language=pt-BR").stream()
            .limit(qtd)
            .toList();
    }

    @Override
    protected Map<String, String> getDefaultHeaders() {
        return Map.of("Authorization", "Bearer " + tmdbToken);
    }

    @Override
    protected List<FilmeDTO> converter(List<Map<String, Object>> items) {
        Map<Integer, String> generos = obterGeneros();
        List<FilmeDTO> lista = new ArrayList<>();
        for (Map<String, Object> item : items) {
            FilmeDTO dto = new FilmeDTO();
            dto.setTitle((String) item.get("title"));
            dto.setSubtitle((String) item.get("original_title"));
            dto.setDescription((String) item.get("overview"));
            dto.setThumbnail("https://image.tmdb.org/t/p/w500" + item.get("poster_path"));

            List<Integer> genreIds = (List<Integer>) item.get("genre_ids");
            List<String> names = genreIds != null
                ? genreIds.stream().map(generos::get).filter(Objects::nonNull).toList()
                : Collections.emptyList();

            dto.setCategories(names);
            lista.add(dto);
        }
        return lista;
    }

    @Override
    protected String getResultsKey() {
        return "results";
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

}

