package br.imd.mybookplace.services;

import br.imd.mybookplace.entities.LivroFavorito;
import br.imd.mybookplace.entities.User;
import br.imd.mybookplace.repositories.LivroFavoritoRepository;
import br.imd.mybookplace.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LivroFavoritoServiceTest {

    private LivroFavoritoRepository livroFavoritoRepository;
    private UserRepository userRepository;
    private LivroFavoritoService livroFavoritoService;

    @BeforeEach
    void setUp() {
        livroFavoritoRepository = mock(LivroFavoritoRepository.class);
        userRepository = mock(UserRepository.class);
        livroFavoritoService = new LivroFavoritoService(livroFavoritoRepository, userRepository);
    }

    private User criarUsuario(String userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }

    private LivroFavorito criarLivroFavorito(User user, String title, String author, String isbn, String thumbnailUrl) {
        return new LivroFavorito(user, title, author, isbn, thumbnailUrl);
    }

    private void mockUsuarioExistente(String userId, User user) {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    }

    private void mockLivroFavoritoExistente(User user, String isbn, LivroFavorito livroFavorito) {
        when(livroFavoritoRepository.findByUserAndIsbn(user, isbn)).thenReturn(Optional.of(livroFavorito));
    }

    private void mockLivroFavoritoInexistente(User user, String isbn) {
        when(livroFavoritoRepository.findByUserAndIsbn(user, isbn)).thenReturn(Optional.empty());
    }

    @Test
    void adicionarLivroFavorito_DeveAdicionarLivroQuandoNaoExistir() {
        String userId = "123";
        String title = "Livro Teste";
        String author = "Autor Teste";
        String thumbnailUrl = "http://imagem.com";
        String isbn = "123456789";

        User user = criarUsuario(userId);
        LivroFavorito livroFavorito = criarLivroFavorito(user, title, author, isbn, thumbnailUrl);

        mockUsuarioExistente(userId, user);
        mockLivroFavoritoInexistente(user, isbn);
        when(livroFavoritoRepository.save(any(LivroFavorito.class))).thenReturn(livroFavorito);

        LivroFavorito resultado = livroFavoritoService.adicionarLivroFavorito(userId, title, author, thumbnailUrl, isbn);

        assertNotNull(resultado);
        assertEquals(title, resultado.getTitle());
        assertEquals(author, resultado.getAuthor());
        assertEquals(isbn, resultado.getIsbn());
        verify(livroFavoritoRepository, times(1)).save(any(LivroFavorito.class));
    }

    @Test
    void adicionarLivroFavorito_DeveLancarExcecaoQuandoLivroJaExistir() {
        String userId = "123";
        String isbn = "123456789";

        User user = criarUsuario(userId);
        LivroFavorito livroExistente = criarLivroFavorito(user, "Livro Teste", "Autor Teste", isbn, "http://imagem.com");

        mockUsuarioExistente(userId, user);
        mockLivroFavoritoExistente(user, isbn, livroExistente);

        assertThrows(IllegalArgumentException.class, () -> {
            livroFavoritoService.adicionarLivroFavorito(userId, "Livro Teste", "Autor Teste", "http://imagem.com", isbn);
        });
    }

    @Test
    void listarFavoritosPorUsuario_DeveRetornarListaDeFavoritos() {
        String userId = "123";
        User user = criarUsuario(userId);
        LivroFavorito livro1 = criarLivroFavorito(user, "Livro 1", "Autor 1", "123456789", "http://imagem1.com");
        LivroFavorito livro2 = criarLivroFavorito(user, "Livro 2", "Autor 2", "987654321", "http://imagem2.com");

        mockUsuarioExistente(userId, user);
        when(livroFavoritoRepository.findByUser(user)).thenReturn(List.of(livro1, livro2));

        List<LivroFavorito> resultado = livroFavoritoService.listarFavoritosPorUsuario(userId);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(livro1));
        assertTrue(resultado.contains(livro2));
        verify(livroFavoritoRepository, times(1)).findByUser(user);
    }

    @Test
    void listarFavoritosPorUsuario_DeveRetornarListaVaziaQuandoNaoHouverFavoritos() {
        String userId = "123";
        User user = criarUsuario(userId);

        mockUsuarioExistente(userId, user);
        when(livroFavoritoRepository.findByUser(user)).thenReturn(List.of());

        List<LivroFavorito> resultado = livroFavoritoService.listarFavoritosPorUsuario(userId);

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(livroFavoritoRepository, times(1)).findByUser(user);
    }

    @Test
    void removerLivroFavorito_DeveRemoverLivroQuandoExistir() {
        String userId = "123";
        String isbn = "123456789";

        User user = criarUsuario(userId);
        LivroFavorito livroFavorito = criarLivroFavorito(user, "Livro Teste", "Autor Teste", isbn, "http://imagem.com");

        mockUsuarioExistente(userId, user);
        mockLivroFavoritoExistente(user, isbn, livroFavorito);

        livroFavoritoService.removerLivroFavorito(userId, isbn);

        verify(livroFavoritoRepository, times(1)).delete(livroFavorito);
    }

    @Test
    void removerLivroFavorito_DeveLancarExcecaoQuandoLivroNaoExistir() {
        String userId = "123";
        String isbn = "123456789";

        User user = criarUsuario(userId);

        mockUsuarioExistente(userId, user);
        mockLivroFavoritoInexistente(user, isbn);

        assertThrows(IllegalArgumentException.class, () -> {
            livroFavoritoService.removerLivroFavorito(userId, isbn);
        });

        verify(livroFavoritoRepository, never()).delete(any(LivroFavorito.class));
    }
}