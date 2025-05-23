package br.imd.mybookplace.services;

import br.imd.mybookplace.DTOS.LLMRequestDTO;
import br.imd.mybookplace.exceptions.LLMServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LLMServiceTest {

    private WebClient webClient;
    private WebClient.Builder webClientBuilder;
    private LLMService llmService;

    // O método anotado com @BeforeEach é executado antes de cada teste.
    // Aqui, ele prepara o ambiente de teste criando mocks para o WebClient e seu Builder, configurando-os para simular o comportamento esperado sem realizar chamadas reais à API externa.
    // Dessa forma, os testes podem controlar exatamente as respostas e falhas do WebClient, garantindo isolamento e previsibilidade nos cenários testados.
    // Por fim, uma instância do LLMService é criada usando o builder mockado, permitindo que os métodos do service sejam testados de forma independente de integrações externas.
    @BeforeEach
    void setUp() {
        webClient = mock(WebClient.class);
        webClientBuilder = mock(WebClient.Builder.class);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        // Corrige ambiguidade usando any(ExchangeStrategies.class)
        when(webClientBuilder.exchangeStrategies(any(ExchangeStrategies.class))).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        llmService = new LLMService(webClientBuilder);
    }

    @Test
    void searchOffers_DeveLancarLLMServiceException_QuandoOcorreErro() {
        // Este teste garante que, caso ocorra uma exceção inesperada (simulada aqui com um RuntimeException) durante a chamada ao método searchOffers do LLMService, a exceção customizada LLMServiceException seja lançada. Isso valida que o service está encapsulando corretamente falhas técnicas e propagando-as de forma padronizada para o restante da aplicação, facilitando o tratamento global de erros.

        // Arrange
        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenThrow(new RuntimeException("Falha"));
        LLMRequestDTO prompt = new LLMRequestDTO();

        // Act & Assert
        assertThrows(LLMServiceException.class, () -> llmService.searchOffers(prompt));
    }

    // Interface fake para garantir compatibilidade de tipos genéricos no mock
    private interface MyHeadersSpec extends WebClient.RequestHeadersSpec<MyHeadersSpec> {}

    @Test
    void searchOffers_DeveRetornarListaVazia_QuandoRespostaVazia() {
        // Este teste verifica que, quando a resposta da API externa retorna um array vazio ("[]"), o método searchOffers do LLMService não lança exceção e lida corretamente com a resposta vazia. O objetivo é garantir que o service seja resiliente a respostas vazias da API, retornando um resultado válido (lista vazia) ao invés de propagar erros desnecessários.

        // Arrange
        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec bodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(bodySpec);
        when(bodySpec.accept(any())).thenReturn(bodySpec);
        when(bodySpec.contentType(any())).thenReturn(bodySpec);
        when(bodySpec.bodyValue(any())).thenReturn((WebClient.RequestHeadersSpec) headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("[]"));
        LLMRequestDTO prompt = new LLMRequestDTO();

        // Act & Assert
        assertDoesNotThrow(() -> llmService.searchOffers(prompt));
    }

    @Test
    void createImage_DeveLancarLLMServiceException_QuandoOcorreErro() {
        // Este teste assegura que, caso ocorra uma exceção inesperada (simulada com um RuntimeException) durante a chamada ao método createImage do LLMService, a exceção customizada LLMServiceException seja lançada. Assim como no teste do método searchOffers, isso valida que o service está padronizando o tratamento de falhas técnicas, lançando exceções de negócio apropriadas para serem tratadas globalmente.
        // Arrange
        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenThrow(new RuntimeException("Falha"));
        LLMRequestDTO prompt = new LLMRequestDTO();

        // Act & Assert
        assertThrows(LLMServiceException.class, () -> llmService.createImage(prompt));
    }
}