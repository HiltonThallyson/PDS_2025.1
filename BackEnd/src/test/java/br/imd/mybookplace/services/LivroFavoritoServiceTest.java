package br.imd.mybookplace.services;

import br.imd.mybookplace.entities.LivroFavorito;
import br.imd.mybookplace.entities.User;
import br.imd.mybookplace.repositories.LivroFavoritoRepository;
import br.imd.mybookplace.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    void adicionarLivroFavorito_DeveAdicionarLivroQuandoNaoExistir() {
    // Arrange
    String userId = "123";
    String title = "Livro Teste";
    String author = "Autor Teste";
    String thumbnailUrl = "http://imagem.com";
    String isbn = "123456789";

    User user = new User();
    user.setId(userId);

    LivroFavorito livroFavorito = new LivroFavorito(user, title, author, isbn, thumbnailUrl);

    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    when(livroFavoritoRepository.findByUserAndIsbn(user, isbn)).thenReturn(Optional.empty());
    when(livroFavoritoRepository.save(any(LivroFavorito.class))).thenReturn(livroFavorito); // Configura o mock para retornar o objeto salvo

    // Act
    LivroFavorito resultado = livroFavoritoService.adicionarLivroFavorito(userId, title, author, thumbnailUrl, isbn);

    // Assert
    assertNotNull(resultado); // Verifica que o resultado não é nulo
    assertEquals(title, resultado.getTitle());
    assertEquals(author, resultado.getAuthor());
    assertEquals(isbn, resultado.getIsbn());
    verify(livroFavoritoRepository, times(1)).save(any(LivroFavorito.class)); // Verifica que o método save foi chamado
}

    @Test
    void adicionarLivroFavorito_DeveLancarExcecaoQuandoLivroJaExistir() {
        // Arrange
        String userId = "123";
        String isbn = "123456789";

        User user = new User();
        user.setId(userId);

        LivroFavorito livroExistente = new LivroFavorito();
        livroExistente.setIsbn(isbn);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(livroFavoritoRepository.findByUserAndIsbn(user, isbn)).thenReturn(Optional.of(livroExistente));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            livroFavoritoService.adicionarLivroFavorito(userId, "Livro Teste", "Autor Teste", "http://imagem.com", isbn);
        });
    }

    @Test
    void listarFavoritosPorUsuario_DeveRetornarListaDeFavoritos(){
        //Arrange
        String userId = "123";

        User user = new User();
        user.setId(userId);

        LivroFavorito livro1 = new LivroFavorito(user, "Livro 1", "Autor 1", "123456789", "http://imagem1.com");
        LivroFavorito livro2 = new LivroFavorito(user, "Livro 2", "Autor 2", "987654321", "http://imagem2.com");

        List<LivroFavorito> listaFavoritos = List.of(livro1, livro2);
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(livroFavoritoRepository.findByUser(user)).thenReturn(listaFavoritos);

        //Act
        List<LivroFavorito> resultado = livroFavoritoService.listarFavoritosPorUsuario(userId);

        //Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Livro 1", resultado.get(0).getTitle());
        assertEquals("Livro 2", resultado.get(1).getTitle());
        verify(livroFavoritoRepository, times(1)).findByUser(user);
    }

    @Test
    void listarFavoritosPorUsuario_DeveRetornarListaDeFavoritosVazia(){
        //Arrange
        String userId = "123";
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(livroFavoritoRepository.findByUser(user)).thenReturn(List.of());

        //Act
        List<LivroFavorito> resultado = livroFavoritoService.listarFavoritosPorUsuario(userId);

        //Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(livroFavoritoRepository, times(1)).findByUser(user);
    }
}
