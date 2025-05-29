package br.imd.mybookplace.controllers;

import br.imd.mybookplace.DTOS.FavoriteBookDTO;
import br.imd.mybookplace.entities.LivroFavorito;
import br.imd.mybookplace.entities.StatusLeitura;
import br.imd.mybookplace.services.LivroFavoritoService;
import br.imd.mybookplace.security.filters.SecurityFilter;
import br.imd.mybookplace.services.TokenService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = LivroFavoritoController.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityFilter.class)
)
@AutoConfigureMockMvc(addFilters = false)
class LivroFavoritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroFavoritoService livroFavoritoService;

    @MockBean
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Long userId = 123L;
    private final String title = "Livro Teste";
    private final String author = "Autor Teste";
    private final String thumbnailUrl = "http://imagem.com";
    private final String isbn = "123456789";

    private LivroFavorito criarLivroFavorito() {
        LivroFavorito livroFavorito = new LivroFavorito();
        livroFavorito.setTitle(title);
        livroFavorito.setAuthor(author);
        livroFavorito.setIsbn(isbn);
        livroFavorito.setThumbnailUrl(thumbnailUrl);
        return livroFavorito;
    }

    @Test
    void adicionarLivroFavorito_DeveAdicionarELancarCreated() throws Exception {
        // Este teste garante que, ao adicionar um livro favorito com sucesso, o endpoint retorna status 201 (Created) e os dados do livro favorito adicionado.
        // Simula o fluxo normal de adição de favorito.
        
        // Arrange
        FavoriteBookDTO requestDto = new FavoriteBookDTO(title, author, thumbnailUrl, isbn, StatusLeitura.QUERO_LER);

        LivroFavorito livroFavoritoEntity = criarLivroFavorito();
        
        when(livroFavoritoService.adicionarLivroFavorito(
                eq(userId), eq(title), eq(author), eq(thumbnailUrl), eq(isbn)
        ))
        .thenReturn(livroFavoritoEntity);

        // Act & Assert
        mockMvc.perform(post("/api/livros-favoritos/register-book/" + userId)
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.isbn").value(isbn));
    }

    @Test
    void adicionarLivroFavorito_DeveRetornarBadRequestQuandoFalhar() throws Exception {
        // Este teste garante que, se ocorrer uma exceção ao adicionar um livro favorito, o endpoint retorna status 400 (Bad Request).
        // Simula uma falha no service ao tentar adicionar o favorito.
        
        // Arrange
        // O DTO de entrada deve ser válido para garantir que o erro 400 venha da exceção do serviço, não da validação do DTO.
        FavoriteBookDTO requestDto = new FavoriteBookDTO(title, author, thumbnailUrl, isbn, StatusLeitura.QUERO_LER);
        when(livroFavoritoService.adicionarLivroFavorito(
                eq(userId), eq(title), eq(author), eq(thumbnailUrl), eq(isbn)
        ))
        .thenThrow(new RuntimeException("Erro ao adicionar livro favorito"));

        // Act & Assert
        mockMvc.perform(post("/api/livros-favoritos/register-book/" + userId)
                        .header("Authorization", "Bearer test-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarFavoritosPorUsuario_DeveRetornarOkComLista() throws Exception {
        // Este teste garante que, ao listar os favoritos de um usuário com sucesso, o endpoint retorna status 200 (OK) e a lista de livros favoritos.
        // Simula o fluxo normal de listagem.
        
        // Arrange
        LivroFavorito livroFavorito = criarLivroFavorito();
        when(livroFavoritoService.listarFavoritosPorUsuario(eq(userId)))
                .thenReturn(List.of(livroFavorito));

        // Act & Assert
        mockMvc.perform(get("/api/livros-favoritos/" + userId)
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(title))
                .andExpect(jsonPath("$[0].author").value(author))
                .andExpect(jsonPath("$[0].isbn").value(isbn));
    }

    @Test
    void listarFavoritosPorUsuario_DeveRetornarBadRequestQuandoFalhar() throws Exception {
        // Este teste garante que, se ocorrer uma exceção ao listar favoritos, o endpoint retorna status 400 (Bad Request).
        // Simula uma falha no service ao tentar listar os favoritos.
        
        // Arrange
        when(livroFavoritoService.listarFavoritosPorUsuario(eq(userId)))
                .thenThrow(new RuntimeException("Erro ao listar favoritos"));

        // Act & Assert
        mockMvc.perform(get("/api/livros-favoritos/" + userId)
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removerLivroFavorito_DeveRemoverELancarNoContent() throws Exception {
        // Este teste garante que, ao remover um livro favorito com sucesso, o endpoint retorna status 204 (No Content).
        // Simula o fluxo normal de remoção de favorito.
        
        // Arrange
        doNothing()
        .when(livroFavoritoService).removerLivroFavorito(eq(userId), eq(isbn));

        // Act & Assert
        mockMvc.perform(delete("/api/livros-favoritos/" + userId + "/" + isbn)
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isNoContent());
    }

    @Test
    void removerLivroFavorito_DeveRetornarBadRequestQuandoFalhar() throws Exception {
        // Este teste garante que, se ocorrer uma exceção ao remover um favorito, o endpoint retorna status 400 (Bad Request).
        // Simula uma falha no service ao tentar remover o favorito.
        
        // Arrange
        doThrow(new RuntimeException("Erro ao remover favorito"))
                .when(livroFavoritoService).removerLivroFavorito(eq(userId), eq(isbn));

        // Act & Assert
        mockMvc.perform(delete("/api/livros-favoritos/" + userId + "/" + isbn)
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarFavoritosAgrupadosPorStatus_DeveRetornarOkComAgrupamento() throws Exception {
        // Este teste garante que o endpoint de agrupamento retorna status 200 (OK) e os livros favoritos agrupados corretamente por status de leitura.
        // Simula dois livros em diferentes status e verifica o agrupamento e o campo statusLeitura no JSON de resposta.

        // Arrange
        LivroFavorito livro1 = criarLivroFavorito();
        livro1.setStatusLeitura(StatusLeitura.QUERO_LER);

        LivroFavorito livro2 = criarLivroFavorito();
        livro2.setStatusLeitura(StatusLeitura.LENDO);

        var agrupados = java.util.Map.of(
                StatusLeitura.QUERO_LER, List.of(livro1),
                StatusLeitura.LENDO, List.of(livro2)
        );

        when(livroFavoritoService.listarFavoritosPorStatus(eq(userId)))
                .thenReturn(agrupados);
        
        // Act & Assert
        mockMvc.perform(get("/api/livros-favoritos/agrupados/" + userId)
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.QUERO_LER[0].title").value(title))
                .andExpect(jsonPath("$.QUERO_LER[0].statusLeitura").value("QUERO_LER"))
                .andExpect(jsonPath("$.LENDO[0].title").value(title))
                .andExpect(jsonPath("$.LENDO[0].statusLeitura").value("LENDO"));
    }

    @Test
    void listarFavoritosAgrupadosPorStatus_DeveRetornarBadRequestQuandoFalhar() throws Exception {
        // Este teste garante que, se ocorrer uma exceção ao agrupar favoritos, o endpoint retorna status 400 (Bad Request).

        // Arrange
        when(livroFavoritoService.listarFavoritosPorStatus(eq(userId)))
                .thenThrow(new RuntimeException("Erro ao agrupar favoritos"));
        
        // Act & Assert
        mockMvc.perform(get("/api/livros-favoritos/agrupados/" + userId)
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void atualizarStatusLeitura_DeveAtualizarELancarNoContent() throws Exception {
        // Este teste garante que, ao atualizar o status de leitura de um livro favorito, o endpoint retorna status 204 (No Content).

        // Arrange
        doNothing()
        .when(livroFavoritoService)
                .atualizarStatusLeitura(eq(userId), eq(isbn), eq(StatusLeitura.LENDO));

        // Act & Assert
        mockMvc.perform(patch("/api/livros-favoritos/" + userId + "/" + isbn + "/status")
                        .header("Authorization", "Bearer test-token")
                        .param("statusLeitura", "LENDO"))
                .andExpect(status().isNoContent());
    }

    @Test
    void atualizarStatusLeitura_DeveRetornarBadRequestQuandoFalhar() throws Exception {
        // Este teste garante que, se ocorrer uma exceção ao atualizar o status de leitura, o endpoint retorna status 400 (Bad Request).

        // Arrange
        doThrow(new RuntimeException("Erro ao atualizar status"))
        .when(livroFavoritoService)
                .atualizarStatusLeitura(eq(userId), eq(isbn), eq(StatusLeitura.LIDO));

        // Act & Assert
        mockMvc.perform(patch("/api/livros-favoritos/" + userId + "/" + isbn + "/status")
                        .header("Authorization", "Bearer test-token")
                        .param("statusLeitura", "LIDO"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listarFavoritosPorUsuario_DeveRetornarStatusLeituraNoDTO() throws Exception {
        // Este teste garante que o campo statusLeitura está presente e correto no DTO retornado pela listagem de favoritos.

        // Arrange
        LivroFavorito livroFavorito = criarLivroFavorito();

        livroFavorito.setStatusLeitura(StatusLeitura.LIDO);

        when(livroFavoritoService.listarFavoritosPorUsuario(eq(userId)))
        .thenReturn(List.of(livroFavorito));
        // Act & Assert
        mockMvc.perform(
                get("/api/livros-favoritos/" + userId)
                        .header("Authorization", "Bearer test-token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].statusLeitura").value("LIDO"));
    }
}