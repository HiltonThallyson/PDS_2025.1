package br.imd.framework.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.imd.framework.DTOs.ProdutoDTO;
import br.imd.framework.entities.Produto;
import br.imd.framework.enums.ProdutoStatus;
import br.imd.framework.services.FavoritoService;

@RestController
@RequestMapping("/api/favoritos")
abstract public class BaseFavoritoController<T extends ProdutoDTO, S extends FavoritoService<T>>{

    protected abstract T mapProdutoToDTO(T produto);


    protected final S favoritoService;
    
    public BaseFavoritoController(S favoriteService) {
        this.favoritoService = favoriteService;
    }

    public ResponseEntity<Produto> adicionarProdutoFavorito(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId,
            @RequestBody T produtoDTO
            ) {

            Produto produtoFavorito = favoritoService.adicionarProdutoFavorito(
                userId,
                produtoDTO
                );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoFavorito);
    }

    /**
     * Lista todos os livros favoritos de um usuário.
     *
     * @param userId ID do usuário.
     * @return ResponseEntity com a lista de livros favoritos do usuário.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<T>> listarFavoritosPorUsuario(
    @RequestHeader("Authorization") String authorizationHeader, 
    @PathVariable Long userId) {
       
            List<T> produtosFavoritos = favoritoService.listarFavoritosPorUsuario(userId);

            // List<ProdutoDTO> livrosFavoritosDTO = produtosFavoritos.stream()
            //     .map(livro -> new FavoriteBookDTO(
            //         livro.getTitle(),
            //         livro.getAuthor(),
            //         livro.getThumbnailUrl(),
            //         livro.getIsbn(),
            //         livro.getStatusLeitura()))
            //     .toList();
            
            // return ResponseEntity.ok(livrosFavoritosDTO);
            return ResponseEntity.ok(produtosFavoritos);
    }

    /**
     * Remove um livro da lista de favoritos do usuário.
     *
     * @param userId ID do usuário.
     * @param isbn ISBN do livro a ser removido.
     * @return ResponseEntity com status 204 (No Content) em caso de sucesso.
     */
    @DeleteMapping("/{userId}/{isbn}")
    public ResponseEntity<Void> removerLivroFavorito(
        @RequestHeader("Authorization") String authorizationHeader, 
        @PathVariable Long userId, 
        @PathVariable Long produtoId) {
            
        favoritoService.removerProdutoFavorito(userId, produtoId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos os livros favoritos do usuário agrupados por status de leitura.
     *
     * @param userId ID do usuário.
     * @return ResponseEntity com mapa de listas de favoritos agrupados por status.
     */
    @GetMapping("/agrupados/{userId}")
    public ResponseEntity<Map<ProdutoStatus, List<T>>> listarFavoritosAgrupadosPorStatus(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId) {


        Map<ProdutoStatus, List<T>> agrupados = favoritoService.listarFavoritosAgrupadosPorStatus(userId);

        Map<ProdutoStatus, List<T>> agrupadosDTO = agrupados.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream()
                        .map(this::mapProdutoToDTO)
                        .collect(Collectors.toList())
            ));

        return ResponseEntity.ok(agrupadosDTO);
    }

    /**
     * Atualiza o status de leitura de um livro favorito do usuário.
     *
     * @param userId ID do usuário.
     * @param isbn ISBN do livro.
     * @param statusLeitura Novo status de leitura.
     * @return ResponseEntity com status 204 em caso de sucesso.
     */
    // @PatchMapping("/{userId}/{isbn}/status")
    // public ResponseEntity<Void> atualizarStatusLeitura(
    //         @RequestHeader("Authorization") String authorizationHeader,
    //         @PathVariable Long userId,
    //         @PathVariable String isbn,
    //         @RequestParam StatusLeitura statusLeitura) {

    //     favoritoService.atualizarStatusLeitura(userId, isbn, statusLeitura);
    //     return ResponseEntity.noContent().build();
    // }
}
