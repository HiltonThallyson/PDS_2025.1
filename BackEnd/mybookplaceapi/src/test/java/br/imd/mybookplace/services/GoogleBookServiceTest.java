// package br.imd.mybookplace.services;

// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;

// import java.net.URI;
// import java.util.Map;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.reactive.function.client.WebClientRequestException;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpMethod;

// import br.imd.mybookplace.exceptions.GoogleBooksApiException;

// public class GoogleBookServiceTest {
//    private WebClient webClient;
//    private WebClient.Builder webClientBuilder;
//    private GoogleBookService googleBookService;

//    @BeforeEach
//    public void setUp(){
//        webClient = mock(WebClient.class);
//        webClientBuilder = mock(WebClient.Builder.class);
//        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
//        when(webClientBuilder.build()).thenReturn(webClient);

//        googleBookService = new GoogleBookService(webClientBuilder);
//    }

//     @Test
//     @SuppressWarnings("unchecked")
//     public void buscarLivrosPorTitulo_DeveLancarGoogleBooksApiException_QuandoWebClientFalhar(){
//         // Este teste garante que, se ocorrer uma falha de comunicação com a API do Google Books (simulada aqui com uma exceção do WebClient), o método buscarLivrosPorTitulo() lança uma exceção customizada GoogleBooksApiException. Isso valida o tratamento de erros do service ao consumir APIs externas.
//         // É importante lembrar que o método buscarLivrosPorTitulo() é apenas uma chamada de wrapper para o método buscarLivros(), que é o que de fato faz a chamada à API do GooleBooks

//         //Arrange
//         String titulo = "titulo";

//         WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
//         WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
//         WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

//         when(webClient.get()).thenReturn(uriSpec);
//         when(uriSpec.uri(anyString())).thenReturn(headersSpec);
//         when(headersSpec.retrieve()).thenReturn(responseSpec);
//         when(responseSpec.bodyToMono(Map.class))
//             .thenThrow(
//                 new WebClientRequestException(
//                     new RuntimeException("Falha"),
//                     HttpMethod.GET,
//                     URI.create("http://fake-url"),
//                     HttpHeaders.EMPTY
//                 )
//             );
//         //Act & Assert
//         assertThrows(GoogleBooksApiException.class, () -> {
//             googleBookService.buscarLivrosPorTitulo(titulo);
//         });
//     }
// }
