// package br.imd.mybookplace.services;

// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Service;

// import br.imd.framework.entities.User;
// import br.imd.framework.repositories.UserRepository;
// import br.imd.mybookplace.entities.LivroFavorito;
// import br.imd.mybookplace.entities.StatusLeitura;
// import br.imd.mybookplace.exceptions.LivroFavoritoException;
// import br.imd.mybookplace.repositories.LivroFavoritoRepository;

// import org.springframework.transaction.annotation.Transactional;

//  /**
//  * Serviço responsável por gerenciar os livros favoritos dos usuários.
//  */
// @Service
// public class LivroFavoritoService {

//     private LivroFavoritoRepository livroFavoritoRepository;
//     private UserRepository userRepository;

//     public LivroFavoritoService(LivroFavoritoRepository livroFavoritoRepository, UserRepository userRepository){
//         this.livroFavoritoRepository = livroFavoritoRepository;
//         this.userRepository = userRepository;
//     }

//     /**
//      * Lista todos os livros favoritos do usuário.
//      *
//      * @param userId ID do usuário.
//      * @return Lista de livros favoritos do usuário.
//      * @throws LivroFavoritoException em caso de erro ao buscar os livros favoritos.
//      */
    
//     @Transactional(readOnly=true)
//     public List<LivroFavorito> listarFavoritosPorUsuario(Long userId){
//         User user = buscarUserPorID(userId);

//         return livroFavoritoRepository.findByUser(user);
//     }

//     @Transactional
//     public LivroFavorito adicionarLivroFavorito(Long userId, String title, String author, String thumbnailUrl, String isbn){
        
//         User user = buscarUserPorID(userId);

//         Optional<LivroFavorito> livroExistente = livroFavoritoRepository.findByUserAndIsbn(user, isbn);

//         if(livroExistente.isPresent()){
//             throw new LivroFavoritoException("Livro já está na lista de favoritos");
//         }

//         LivroFavorito livroFavorito = criarLivroFavorito(user, title, author, thumbnailUrl, isbn);
        
//         return livroFavoritoRepository.save(livroFavorito);
//     }

//     /**
//      * Remove um livro da lista de favoritos do usuário.
//      *
//      * @param userId ID do usuário.
//      * @param isbn ISBN do livro a ser removido.
//      * @throws LivroFavoritoException em caso de erro ao remover o livro dos favoritos.
//      */
//     @Transactional
//     public void removerLivroFavorito(Long userId, String isbn){
//         User user = buscarUserPorID(userId);

//         LivroFavorito livroFavorito = livroFavoritoRepository.findByUserAndIsbn(user, isbn)
//             .orElseThrow(() -> new LivroFavoritoException("Não é possível remover um livro que não está na lista de favoritos"));

//         livroFavoritoRepository.delete(livroFavorito);
//     }

//     /**
//      * Verifica se um livro está na lista de favoritos do usuário.
//      *
//      * @param isbn ISBN do livro.
//      * @param userId ID do usuário.
//      * @return true se o livro está nos favoritos, false caso contrário.
//      * @throws LivroFavoritoException em caso de erro na verificação.
//      */
//     public boolean isLivroFavorito(String isbn, Long userId) {
//         User user = buscarUserPorID(userId);

//         return livroFavoritoRepository.findByUserAndIsbn(user, isbn).isPresent();
//     }

//     /**
//      * Lista todos os livros favoritos do usuário agrupados por status de leitura.
//      *
//      * @param userId ID do usuário.
//      * @return Mapa com a lista de livros favoritos agrupados por StatusLeitura.
//      */
  
//     @Transactional(readOnly=true)
//     public Map<StatusLeitura, List<LivroFavorito>> listarFavoritosPorStatus(Long userId) {
//         User user = buscarUserPorID(userId);
//         List<LivroFavorito> favoritos = livroFavoritoRepository.findByUser(user);
//         return favoritos.stream().collect(Collectors.groupingBy(LivroFavorito::getStatusLeitura));
//     }

//     /**
//      * Atualiza o status de leitura de um livro favorito do usuário.
//      *
//      * @param userId ID do usuário.
//      * @param isbn ISBN do livro.
//      * @param novoStatus Novo status de leitura.
//      * @throws LivroFavoritoException se o livro não for encontrado.
//      */
//     @Transactional
//     public void atualizarStatusLeitura(Long userId, String isbn, StatusLeitura novoStatus) {
//         User user = buscarUserPorID(userId);
//         LivroFavorito livroFavorito = livroFavoritoRepository.findByUserAndIsbn(user, isbn)
//             .orElseThrow(() -> new LivroFavoritoException("Livro não encontrado na lista de favoritos"));
//         livroFavorito.setStatusLeitura(novoStatus);
//         livroFavoritoRepository.save(livroFavorito);
//     }

//    private User buscarUserPorID(Long userId) {
//         User user = userRepository.findById(userId)
//             .orElseThrow(() -> new LivroFavoritoException("Usuário não encontrado com o ID: " + userId));
        
//         return user;
//     }

//     private LivroFavorito criarLivroFavorito(User user, String title, String author, String thumbnailUrl, String isbn) {
//         return new LivroFavorito(user, title, author, isbn, thumbnailUrl);
//     }
// }
