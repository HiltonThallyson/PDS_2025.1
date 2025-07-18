package br.imd.mybookplace.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import br.imd.framework.services.ProdutoService;
import br.imd.mybookplace.DTOS.LivroDTO;

@Service
public class LivroService extends ProdutoService<LivroDTO> {

    private static final String API_URL = "https://www.googleapis.com/books/v1";
    private static final Logger logger = LoggerFactory.getLogger(LivroService.class);

    public LivroService(WebClient.Builder builder) {
        super(builder, API_URL, logger);
    }

    @Override
    public List<LivroDTO> buscarPorNome(String titulo) {
        String url = "/volumes?q=intitle:" + titulo;
        return buscarComConversao(url);
    }

    @Override
    public List<LivroDTO> buscarPorQuantidade(int qtd) {
        String[] categorias = {"history", "science", "technology", "art", "fiction"};
        List<LivroDTO> all = new ArrayList<>();
        for (String c : categorias) {
            String url = "/volumes?q=subject:" + c + "&maxResults=" + qtd;
            all.addAll(buscarComConversao(url));
        }
        return all;
    }

    @Override
    protected List<LivroDTO> converter(List<Map<String, Object>> items) {
        List<LivroDTO> lista = new ArrayList<>();
        for (Map<String, Object> item : items) {
            Map<String, Object> info = (Map<String, Object>) item.get("volumeInfo");
            LivroDTO dto = new LivroDTO();
            dto.setTitle((String) info.get("title"));
            dto.setSubtitle((String) info.get("subtitle"));
            dto.setAuthors((List<String>) info.get("authors"));
            dto.setEditora((String) info.get("publisher"));
            dto.setDescription((String) info.get("description"));
            dto.setCategories((List<String>) info.get("categories"));

            Map<String, String> imageLinks = (Map<String, String>) info.get("imageLinks");
            if (imageLinks != null) dto.setThumbnail(imageLinks.get("thumbnail"));

            List<Map<String, String>> identifiers = (List<Map<String, String>>) info.get("industryIdentifiers");
            if (identifiers != null) {
                for (Map<String, String> idMap : identifiers) {
                    if ("ISBN_13".equals(idMap.get("type"))) {
                        dto.setIsbn(idMap.get("identifier"));
                        break;
                    }
                }
            }

            lista.add(dto);
        }
        return lista;
    }

    @Override
    protected String getResultsKey() {
        return "items";
    }
}



