package br.imd.mybookplace.controllers;

import br.imd.mybookplace.entities.LivroFavorito;
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

    private final String userId = "123";
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
        LivroFavorito livroFavorito = criarLivroFavorito();
        when(livroFavoritoService.adicionarLivroFavorito(
                eq(userId), eq(title), eq(author), eq(thumbnailUrl), eq(isbn)
        )).thenReturn(livroFavorito);

        // Act & Assert
        mockMvc.perform(post("/livros-favoritos")
                        .param("userId", userId)
                        .param("title", title)
                        .param("author", author)
                        .param("thumbnailUrl", thumbnailUrl)
                        .param("isbn", isbn)
                        .contentType(MediaType.APPLICATION_JSON))
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
        when(livroFavoritoService.adicionarLivroFavorito(
                eq(userId), eq(title), eq(author), eq(thumbnailUrl), eq(isbn)
        )).thenThrow(new RuntimeException("Erro ao adicionar livro favorito"));

        // Act & Assert
        mockMvc.perform(post("/livros-favoritos")
                        .param("userId", userId)
                        .param("title", title)
                        .param("author", author)
                        .param("thumbnailUrl", thumbnailUrl)
                        .param("isbn", isbn)
                        .contentType(MediaType.APPLICATION_JSON))
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
        mockMvc.perform(get("/livros-favoritos/" + userId))
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
        mockMvc.perform(get("/livros-favoritos/" + userId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removerLivroFavorito_DeveRemoverELancarNoContent() throws Exception {
        // Este teste garante que, ao remover um livro favorito com sucesso, o endpoint retorna status 204 (No Content).
        // Simula o fluxo normal de remoção de favorito.
        
        // Arrange
        doNothing().when(livroFavoritoService).removerLivroFavorito(eq(userId), eq(isbn));

        // Act & Assert
        mockMvc.perform(delete("/livros-favoritos/" + userId + "/" + isbn))
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
        mockMvc.perform(delete("/livros-favoritos/" + userId + "/" + isbn))
                .andExpect(status().isBadRequest());
    }
}