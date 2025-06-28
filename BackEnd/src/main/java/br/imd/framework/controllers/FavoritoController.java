// package br.imd.framework.controllers;

// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import br.imd.framework.DTOs.FavoritoProdutoDTO;
// import br.imd.framework.DTOs.ProdutoDTO;
// import br.imd.framework.entities.Produto;
// import br.imd.framework.services.FavoritoService;
// import br.imd.mybookplace.DTOS.FavoriteBookDTO;
// import br.imd.mybookplace.entities.LivroFavorito;
// import br.imd.mybookplace.entities.StatusLeitura;
// import br.imd.mybookplace.services.LivroFavoritoService;

// @RestController
// @RequestMapping("/api/favoritos")
// public class FavoritoController {


//     private final FavoritoService favoritoService;

//     public FavoritoController(FavoritoService livroFavoritoService) {
//         this.favoritoService = livroFavoritoService;
//     }

//     public ResponseEntity<ProdutoDTO> adicionarProdutoFavorito(
//             @RequestHeader("Authorization") String authorizationHeader,
//             @PathVariable Long userId,
//             @RequestBody ProdutoDTO produtoDTO
//             ) {

//             ProdutoDTO produtoFavorito = favoritoService.adicionarProdutoFavorito(
//                 userId,
//                 produtoDTO
//                 );
            
//             return ResponseEntity.status(HttpStatus.CREATED).body(produtoFavorito);
//     }

//     /**
//      * Lista todos os livros favoritos de um usuário.
//      *
//      * @param userId ID do usuário.
//      * @return ResponseEntity com a lista de livros favoritos do usuário.
//      */
//     @GetMapping("/{userId}")
//     public ResponseEntity<List<ProdutoDTO>> listarFavoritosPorUsuario(
//     @RequestHeader("Authorization") String authorizationHeader, 
//     @PathVariable Long userId) {
       
//             List<ProdutoDTO> produtosFavoritos = favoritoService.listarFavoritosPorUsuario(userId);

//             // List<ProdutoDTO> livrosFavoritosDTO = produtosFavoritos.stream()
//             //     .map(livro -> new FavoriteBookDTO(
//             //         livro.getTitle(),
//             //         livro.getAuthor(),
//             //         livro.getThumbnailUrl(),
//             //         livro.getIsbn(),
//             //         livro.getStatusLeitura()))
//             //     .toList();
            
//             // return ResponseEntity.ok(livrosFavoritosDTO);
//             return ResponseEntity.ok(produtosFavoritos);
//     }

//     /**
//      * Remove um livro da lista de favoritos do usuário.
//      *
//      * @param userId ID do usuário.
//      * @param isbn ISBN do livro a ser removido.
//      * @return ResponseEntity com status 204 (No Content) em caso de sucesso.
//      */
//     @DeleteMapping("/{userId}/{isbn}")
//     public ResponseEntity<Void> removerLivroFavorito(
//         @RequestHeader("Authorization") String authorizationHeader, 
//         @PathVariable Long userId, 
//         @PathVariable String isbn) {
            
//         favoritoService.removerLivroFavorito(userId, isbn);
//         return ResponseEntity.noContent().build();
//     }

//     /**
//      * Lista todos os livros favoritos do usuário agrupados por status de leitura.
//      *
//      * @param userId ID do usuário.
//      * @return ResponseEntity com mapa de listas de favoritos agrupados por status.
//      */
//     @GetMapping("/agrupados/{userId}")
//     public ResponseEntity<Map<StatusLeitura, List<FavoriteBookDTO>>> listarFavoritosAgrupadosPorStatus(
//             @RequestHeader("Authorization") String authorizationHeader,
//             @PathVariable Long userId) {

//         Map<StatusLeitura, List<LivroFavorito>> agrupados = favoritoService.listarFavoritosPorStatus(userId);

//         Map<StatusLeitura, List<FavoriteBookDTO>> agrupadosDTO = agrupados.entrySet().stream()
//             .collect(Collectors.toMap(
//                 Map.Entry::getKey,
//                 e -> e.getValue().stream()
//                         .map(livro -> new FavoriteBookDTO(
//                                 livro.getTitle(),
//                                 livro.getAuthor(),
//                                 livro.getThumbnailUrl(),
//                                 livro.getIsbn(),
//                                 livro.getStatusLeitura()
//                         ))
//                         .collect(Collectors.toList())
//             ));

//         return ResponseEntity.ok(agrupadosDTO);
//     }

//     /**
//      * Atualiza o status de leitura de um livro favorito do usuário.
//      *
//      * @param userId ID do usuário.
//      * @param isbn ISBN do livro.
//      * @param statusLeitura Novo status de leitura.
//      * @return ResponseEntity com status 204 em caso de sucesso.
//      */
//     @PatchMapping("/{userId}/{isbn}/status")
//     public ResponseEntity<Void> atualizarStatusLeitura(
//             @RequestHeader("Authorization") String authorizationHeader,
//             @PathVariable Long userId,
//             @PathVariable String isbn,
//             @RequestParam StatusLeitura statusLeitura) {

//         favoritoService.atualizarStatusLeitura(userId, isbn, statusLeitura);
//         return ResponseEntity.noContent().build();
//     }
// }
