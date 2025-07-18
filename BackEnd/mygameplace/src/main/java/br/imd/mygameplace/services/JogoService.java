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

import br.imd.framework.exceptions.ApiException;
import br.imd.framework.services.ProdutoService;
import br.imd.mygameplace.DTOS.JogoDTO;

@Service
public class JogoService extends ProdutoService<JogoDTO> {

    private static final String API_URL = "https://www.freetogame.com/api";
    private static final Logger logger = LoggerFactory.getLogger(JogoService.class);

    public JogoService(WebClient.Builder builder) {
        super(builder, API_URL, logger);
    }

    @Override
    public List<JogoDTO> buscarPorNome(String titulo) {
        return buscarComConversao("/games").stream()
            .filter(j -> j.getTitle() != null && j.getTitle().toLowerCase().contains(titulo.toLowerCase()))
            .toList();
    }

    @Override
    public List<JogoDTO> buscarPorQuantidade(int qtdPorCategoria) {
        return buscarComConversao("/games").stream().limit(qtdPorCategoria).toList();
    }

    @Override
    protected List<JogoDTO> converter(List<Map<String, Object>> items) {
        List<JogoDTO> lista = new ArrayList<>();
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
            lista.add(dto);
        }
        return lista;
    }

    @Override
    protected String getResultsKey() {
        return ""; // resposta já vem como lista
    }
}


