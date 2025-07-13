package br.imd.mygameplace.services;

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

import br.imd.framework.exceptions.GoogleBooksApiException;
import br.imd.framework.services.ProdutoService;
import br.imd.mygameplace.DTOS.JogoDTO;

@Service
public class JogoService extends ProdutoService<JogoDTO>{

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(JogoService.class);
    final String API_URL = "https://www.freetogame.com/api";

    public JogoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }

    @Override
    public List<JogoDTO> buscarPorNome(String titulo) {
        // A API do FreeToGame não tem busca por nome via parâmetro, então talvez você precise filtrar manualmente depois
        String url = "/games";
        List<JogoDTO> todosJogos = buscarJogoDTOs(url);

        // Filtrar por nome
        return todosJogos.stream()
                .filter(jogo -> jogo.getTitle() != null && jogo.getTitle().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    @Override
    public List<JogoDTO> buscarPorQuantidade(int qtdPorCategoria) {
        String url = "/games";
        List<JogoDTO> todosJogos = buscarJogoDTOs(url);
        return todosJogos.stream().limit(qtdPorCategoria).toList();
    }

    private List<JogoDTO> buscarJogoDTOs(String url){
        List<Map<String, Object>> items = fazerRequisicaoFreeGames(url);
        return converterParaJogoDTOs(items);
    }

    private List<Map<String, Object>> fazerRequisicaoFreeGames(String url){
        try {
            List<Map<String, Object>> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(List.class)
                .block();

            return response != null ? response : Collections.emptyList();
        } catch (WebClientResponseException | WebClientRequestException e) {
            logger.error("Erro ao acessar a API");
            throw new GoogleBooksApiException("Erro ao acessar a API FreeToGame: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erro inesperado na API FreeToGame: {}", e.getMessage());
            throw new GoogleBooksApiException("Erro inesperado: " + e.getMessage(), e);
        }
    }

    private List<JogoDTO> converterParaJogoDTOs(List<Map<String, Object>> items) {
        List<JogoDTO> JogoDTOs = new ArrayList<>();

        for (Map<String, Object> item : items) {
            JogoDTO dto = new JogoDTO();
            dto.setTitle((String) item.get("title"));
            dto.setSubtitle((String) item.get("freetogame_profile_url"));

            String developer = (String) item.get("developer");
            dto.setDevelopers(developer != null ? List.of(developer) : Collections.emptyList());

            dto.setEditora((String) item.get("publisher"));
            dto.setDescription((String) item.get("short_description"));

            String genre = (String) item.get("genre");
            dto.setCategories(genre != null ? List.of(genre) : Collections.emptyList());

            dto.setThumbnail((String) item.get("thumbnail"));

            JogoDTOs.add(dto);
        }

        return JogoDTOs;
    }
}
