// package br.imd.mygameplace.services;

// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
// import java.util.stream.Collectors;

// import org.springframework.stereotype.Service;

// import br.imd.framework.entities.User;
// import br.imd.framework.repositories.UserRepository;

// import org.springframework.transaction.annotation.Transactional;

//  /**
//  * Serviço responsável por gerenciar os jogos favoritos dos usuários.
//  */
// @Service
// public class JogoFavoritoService {

//     private JogoFavoritoRepository jogoFavoritoRepository;
//     private UserRepository userRepository;

//     public JogoFavoritoService(JogoFavoritoRepository jogoFavoritoRepository, UserRepository userRepository){
//         this.jogoFavoritoRepository = jogoFavoritoRepository;
//         this.userRepository = userRepository;
//     }

//     /**
//      * Lista todos os jogos favoritos do usuário.
//      *
//      * @param userId ID do usuário.
//      * @return Lista de jogos favoritos do usuário.
//      * @throws jogoFavoritoException em caso de erro ao buscar os jogos favoritos.
//      */
    
//     @Transactional(readOnly=true)
//     public List<JogoFavorito> listarFavoritosPorUsuario(Long userId){
//         User user = buscarUserPorID(userId);

//         return jogoFavoritoRepository.findByUser(user);
//     }

//     @Transactional
//     public JogoFavorito adicionarjogoFavorito(Long userId, String title, String desenvolvedores, String thumbnailUrl, String isbn){
        
//         User user = buscarUserPorID(userId);

//         Optional<JogoFavorito> jogoExistente = jogoFavoritoRepository.findByUserAndIsbn(user, isbn);

//         if(jogoExistente.isPresent()){
//             throw new JogoFavoritoException("jogo já está na lista de favoritos");
//         }

//         JogoFavorito jogoFavorito = criarjogoFavorito(user, title, author, thumbnailUrl, isbn);
        
//         return jogoFavoritoRepository.save(jogoFavorito);
//     }

//     /**
//      * Remove um jogo da lista de favoritos do usuário.
//      *
//      * @param userId ID do usuário.
//      * @param isbn ISBN do jogo a ser removido.
//      * @throws jogoFavoritoException em caso de erro ao remover o jogo dos favoritos.
//      */
//     @Transactional
//     public void removerjogoFavorito(Long userId, String isbn){
//         User user = buscarUserPorID(userId);

//         JogoFavorito jogoFavorito = jogoFavoritoRepository.findByUserAndIsbn(user, isbn)
//             .orElseThrow(() -> new jogoFavoritoException("Não é possível remover um jogo que não está na lista de favoritos"));

//         jogoFavoritoRepository.delete(jogoFavorito);
//     }

//     /**
//      * Verifica se um jogo está na lista de favoritos do usuário.
//      *
//      * @param isbn ISBN do jogo.
//      * @param userId ID do usuário.
//      * @return true se o jogo está nos favoritos, false caso contrário.
//      * @throws jogoFavoritoException em caso de erro na verificação.
//      */
//     public boolean isjogoFavorito(String isbn, Long userId) {
//         User user = buscarUserPorID(userId);

//         return jogoFavoritoRepository.findByUserAndIsbn(user, isbn).isPresent();
//     }

//     /**
//      * Lista todos os jogos favoritos do usuário agrupados por status de leitura.
//      *
//      * @param userId ID do usuário.
//      * @return Mapa com a lista de jogos favoritos agrupados por StatusLeitura.
//      */
  
//     @Transactional(readOnly=true)
//     public Map<StatusLeitura, List<jogoFavorito>> listarFavoritosPorStatus(Long userId) {
//         User user = buscarUserPorID(userId);
//         List<jogoFavorito> favoritos = jogoFavoritoRepository.findByUser(user);
//         return favoritos.stream().collect(Collectors.groupingBy(jogoFavorito::getStatusLeitura));
//     }

//     /**
//      * Atualiza o status de leitura de um jogo favorito do usuário.
//      *
//      * @param userId ID do usuário.
//      * @param isbn ISBN do jogo.
//      * @param novoStatus Novo status de leitura.
//      * @throws jogoFavoritoException se o jogo não for encontrado.
//      */
//     @Transactional
//     public void atualizarStatusLeitura(Long userId, String isbn, StatusLeitura novoStatus) {
//         User user = buscarUserPorID(userId);
//         jogoFavorito jogoFavorito = jogoFavoritoRepository.findByUserAndIsbn(user, isbn)
//             .orElseThrow(() -> new jogoFavoritoException("jogo não encontrado na lista de favoritos"));
//         jogoFavorito.setStatusLeitura(novoStatus);
//         jogoFavoritoRepository.save(jogoFavorito);
//     }

//    private User buscarUserPorID(Long userId) {
//         User user = userRepository.findById(userId)
//             .orElseThrow(() -> new jogoFavoritoException("Usuário não encontrado com o ID: " + userId));
        
//         return user;
//     }

//     private jogoFavorito criarjogoFavorito(User user, String title, String author, String thumbnailUrl, String isbn) {
//         return new jogoFavorito(user, title, author, isbn, thumbnailUrl);
//     }
// }
