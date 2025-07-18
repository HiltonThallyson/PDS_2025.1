package br.imd.framework.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import br.imd.framework.exceptions.ApiException;

public abstract class ProdutoService<T> {

    protected final WebClient webClient;
    protected final Logger logger;

    protected ProdutoService(WebClient.Builder builder, String baseUrl, Logger logger) {
        this.webClient = builder.baseUrl(baseUrl).build();
        this.logger = logger;
    }

    public abstract List<T> buscarPorNome(String titulo);
    public abstract List<T> buscarPorQuantidade(int qtdPorCategoria);

    protected abstract List<T> converter(List<Map<String, Object>> dados);
    protected abstract String getResultsKey(); // ex: "items", "results" ou "" para resposta direta como lista
    protected Map<String, String> getDefaultHeaders() {
        return Collections.emptyMap(); // pode ser sobrescrito
    }

    protected List<Map<String, Object>> fazerRequisicao(String urlPath) {
        try {
            WebClient.RequestHeadersSpec<?> request = webClient.get().uri(urlPath);

            for (Map.Entry<String, String> entry : getDefaultHeaders().entrySet()) {
                request = request.header(entry.getKey(), entry.getValue());
            }

            Object rawResponse = request.retrieve().bodyToMono(Object.class).block();

            if (rawResponse instanceof List) {
                return (List<Map<String, Object>>) rawResponse;
            } else if (rawResponse instanceof Map responseMap) {
                String key = getResultsKey();
                if (!key.isEmpty() && responseMap.containsKey(key)) {
                    return (List<Map<String, Object>>) responseMap.get(key);
                }
            }

            return Collections.emptyList();

        } catch (WebClientResponseException | WebClientRequestException e) {
            logger.error("Erro ao acessar a API: {}", e.getMessage());
            throw new ApiException("Erro na API: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erro inesperado: {}", e.getMessage());
            throw new ApiException("Erro inesperado: " + e.getMessage(), e);
        }
    }

    protected List<T> buscarComConversao(String urlPath) {
        List<Map<String, Object>> dados = fazerRequisicao(urlPath);
        return converter(dados);
    }
}
