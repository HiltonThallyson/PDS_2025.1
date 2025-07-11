// package br.imd.mybookplace.services;

// import br.imd.framework.entities.User;
// import br.imd.framework.repositories.UserRepository;
// import br.imd.mybookplace.exceptions.LivroFavoritoException;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.List;
// import java.util.Map;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class LivroFavoritoServiceTest {

//     //Explicar cada um dos métodos de teste
//     private LivroFavoritoRepository livroFavoritoRepository;
//     private UserRepository userRepository;
//     private LivroFavoritoService livroFavoritoService;

//     @BeforeEach
//     void setUp() {
//         livroFavoritoRepository = mock(LivroFavoritoRepository.class);
//         userRepository = mock(UserRepository.class);
//         livroFavoritoService = new LivroFavoritoService(livroFavoritoRepository, userRepository);
//     }

//     private User criarUsuario(Long userId) {
//         User user = new User();
//         user.setId(userId);
//         return user;
//     }

//     private LivroFavorito criarLivroFavorito(User user, String title, String author, String isbn, String thumbnailUrl) {
//         return new LivroFavorito(user, title, author, isbn, thumbnailUrl);
//     }

//     private void mockUsuarioExistente(Long userId, User user) {
//         when(userRepository.findById(userId)).thenReturn(Optional.of(user));
//     }

//     private void mockLivroFavoritoExistente(User user, String isbn, LivroFavorito livroFavorito) {
//         when(livroFavoritoRepository.findByUserAndIsbn(user, isbn)).thenReturn(Optional.of(livroFavorito));
//     }

//     private void mockLivroFavoritoInexistente(User user, String isbn) {
//         when(livroFavoritoRepository.findByUserAndIsbn(user, isbn)).thenReturn(Optional.empty());
//     }

//     @Test
//     void adicionarLivroFavorito_DeveAdicionarLivroQuandoNaoExistir() {

//         Long userId = 123L;

//         // Este teste verifica o comportamento do método adicionarLivroFavorito quando o livro ainda não está na lista de favoritos do usuário.
//         // O cenário simula um usuário existente e um livro que não está cadastrado como favorito.
//         // O teste valida se o livro é adicionado com sucesso à lista de favoritos.

//         String title = "Livro Teste";
//         String author = "Autor Teste";
//         String thumbnailUrl = "http://imagem.com";
//         String isbn = "123456789";

//         User user = criarUsuario(userId);
//         LivroFavorito livroFavorito = criarLivroFavorito(user, title, author, isbn, thumbnailUrl);

//         mockUsuarioExistente(userId, user);
//         mockLivroFavoritoInexistente(user, isbn);
//         when(livroFavoritoRepository.save(any(LivroFavorito.class))).thenReturn(livroFavorito);

//         LivroFavorito resultado = livroFavoritoService.adicionarLivroFavorito(userId, title, author, thumbnailUrl, isbn);

//         assertNotNull(resultado);
//         assertEquals(title, resultado.getTitle());
//         assertEquals(author, resultado.getAuthor());
//         assertEquals(isbn, resultado.getIsbn());
//         verify(livroFavoritoRepository, times(1)).save(any(LivroFavorito.class));
//     }

//     @Test
//     void adicionarLivroFavorito_DeveLancarExcecaoQuandoLivroJaExistir() {

//         Long userId = 123L;

//         // Este teste verifica se uma exceção é lançada ao tentar adicionar um livro que já está na lista de favoritos do usuário.
//         // O cenário simula um usuário existente e um livro que já está cadastrado como favorito.
//         // O teste valida se a exceção LivroFavoritoException é lançada corretamente.


//         String isbn = "123456789";

//         User user = criarUsuario(userId);
//         LivroFavorito livroExistente = criarLivroFavorito(user, "Livro Teste", "Autor Teste", isbn, "http://imagem.com");

//         mockUsuarioExistente(userId, user);
//         mockLivroFavoritoExistente(user, isbn, livroExistente);

//         assertThrows(LivroFavoritoException.class, () -> {
//             livroFavoritoService.adicionarLivroFavorito(userId, "Livro Teste", "Autor Teste", "http://imagem.com", isbn);
//         });
//     }

//     @Test
//     void listarFavoritosPorUsuario_DeveRetornarListaDeFavoritos() {

//         Long userId = 123L;

//         // Este teste verifica se o método listarFavoritosPorUsuario retorna corretamente a lista de livros favoritos de um usuário.
//         // O cenário simula um usuário existente com dois livros favoritos cadastrados.
//         // O teste valida se a lista retornada contém os livros favoritos do usuário.

//         User user = criarUsuario(userId);
//         LivroFavorito livro1 = criarLivroFavorito(user, "Livro 1", "Autor 1", "123456789", "http://imagem1.com");
//         LivroFavorito livro2 = criarLivroFavorito(user, "Livro 2", "Autor 2", "987654321", "http://imagem2.com");

//         mockUsuarioExistente(userId, user);
//         when(livroFavoritoRepository.findByUser(user)).thenReturn(List.of(livro1, livro2));

//         List<LivroFavorito> resultado = livroFavoritoService.listarFavoritosPorUsuario(userId);

//         assertNotNull(resultado);
//         assertEquals(2, resultado.size());
//         assertTrue(resultado.contains(livro1));
//         assertTrue(resultado.contains(livro2));
//         verify(livroFavoritoRepository, times(1)).findByUser(user);
//     }

//     @Test
//     void listarFavoritosPorUsuario_DeveRetornarListaVaziaQuandoNaoHouverFavoritos() {

//         Long userId = 123L;

//         // Este teste verifica o comportamento do método listarFavoritosPorUsuario quando o usuário não possui livros favoritos cadastrados.
//         // O cenário simula um usuário existente sem nenhum livro na lista de favoritos.
//         // O teste valida se o método retorna uma lista vazia corretamente.

//         User user = criarUsuario(userId);

//         mockUsuarioExistente(userId, user);
//         when(livroFavoritoRepository.findByUser(user)).thenReturn(List.of());

//         List<LivroFavorito> resultado = livroFavoritoService.listarFavoritosPorUsuario(userId);

//         assertNotNull(resultado);
//         assertTrue(resultado.isEmpty());
//         verify(livroFavoritoRepository, times(1)).findByUser(user);
//     }

//     @Test
//     void removerLivroFavorito_DeveRemoverLivroQuandoExistir() {

//         Long userId = 123L;

//         // Este teste verifica o comportamento do método removerLivroFavorito quando o livro está na lista de favoritos do usuário.
//         // O cenário simula um usuário existente e um livro que está cadastrado como favorito.
//         // O teste valida se o livro é removido com sucesso da lista de favoritos.

//         String isbn = "123456789";

//         User user = criarUsuario(userId);
//         LivroFavorito livroFavorito = criarLivroFavorito(user, "Livro Teste", "Autor Teste", isbn, "http://imagem.com");

//         mockUsuarioExistente(userId, user);
//         mockLivroFavoritoExistente(user, isbn, livroFavorito);

//         livroFavoritoService.removerLivroFavorito(userId, isbn);

//         verify(livroFavoritoRepository, times(1)).delete(livroFavorito);
//     }

//     @Test
//     void removerLivroFavorito_DeveLancarExcecaoQuandoLivroNaoExistir() {

//         Long userId = 123L;

//         // Este teste verifica se uma exceção é lançada ao tentar remover um livro que não está na lista de favoritos do usuário.
//         // O cenário simula um usuário existente e a tentativa de remoção de um livro que não está cadastrado como favorito.

//         String isbn = "123456789";

//         User user = criarUsuario(userId);

//         mockUsuarioExistente(userId, user);
//         mockLivroFavoritoInexistente(user, isbn);

//         assertThrows(LivroFavoritoException.class, () -> {
//             livroFavoritoService.removerLivroFavorito(userId, isbn);
//         });

//         verify(livroFavoritoRepository, never()).delete(any(LivroFavorito.class));
//     }

//     @Test
//     void listarFavoritosPorStatus_DeveAgruparPorStatusLeitura() {
//         // Arrange
//         Long userId = 1L;
//         User user = criarUsuario(userId);
//         LivroFavorito livro1 = criarLivroFavorito(user, "Livro 1", "Autor 1", "111", "url1");
//         livro1.setStatusLeitura(StatusLeitura.QUERO_LER);
//         LivroFavorito livro2 = criarLivroFavorito(user, "Livro 2", "Autor 2", "222", "url2");
//         livro2.setStatusLeitura(StatusLeitura.LENDO);
//         LivroFavorito livro3 = criarLivroFavorito(user, "Livro 3", "Autor 3", "333", "url3");
//         livro3.setStatusLeitura(StatusLeitura.LIDO);
//         mockUsuarioExistente(userId, user);
//         when(livroFavoritoRepository.findByUser(user)).thenReturn(List.of(livro1, livro2, livro3));

//         // Act
//         Map<StatusLeitura, List<LivroFavorito>> agrupados = livroFavoritoService.listarFavoritosPorStatus(userId);

//         // Assert
//         assertEquals(1, agrupados.get(StatusLeitura.QUERO_LER).size());
//         assertEquals(1, agrupados.get(StatusLeitura.LENDO).size());
//         assertEquals(1, agrupados.get(StatusLeitura.LIDO).size());
//         assertTrue(agrupados.get(StatusLeitura.QUERO_LER).contains(livro1));
//         assertTrue(agrupados.get(StatusLeitura.LENDO).contains(livro2));
//         assertTrue(agrupados.get(StatusLeitura.LIDO).contains(livro3));
//     }

//     @Test
//     void atualizarStatusLeitura_DeveAtualizarStatusQuandoLivroExistir() {
//         // Arrange
//         Long userId = 1L;
//         String isbn = "123";
//         User user = criarUsuario(userId);
//         LivroFavorito livro = criarLivroFavorito(user, "Livro Teste", "Autor Teste", isbn, "url");
//         livro.setStatusLeitura(StatusLeitura.QUERO_LER);
//         mockUsuarioExistente(userId, user);
//         mockLivroFavoritoExistente(user, isbn, livro);
//         when(livroFavoritoRepository.save(any(LivroFavorito.class))).thenReturn(livro);

//         // Act
//         livroFavoritoService.atualizarStatusLeitura(userId, isbn, StatusLeitura.LENDO);

//         // Assert
//         assertEquals(StatusLeitura.LENDO, livro.getStatusLeitura());
//         verify(livroFavoritoRepository, times(1)).save(livro);
//     }

//     @Test
//     void atualizarStatusLeitura_DeveLancarExcecaoQuandoLivroNaoExistir() {
//         // Arrange
//         Long userId = 1L;
//         String isbn = "999";
//         User user = criarUsuario(userId);
//         mockUsuarioExistente(userId, user);
//         mockLivroFavoritoInexistente(user, isbn);

//         // Assert
//         assertThrows(LivroFavoritoException.class, () -> {
//             livroFavoritoService.atualizarStatusLeitura(userId, isbn, StatusLeitura.LIDO);
//         });
//         verify(livroFavoritoRepository, never()).save(any(LivroFavorito.class));
//     }
// }