package br.imd.mybookplace.controllers;

import br.imd.mybookplace.DTOS.FavoriteBookDTO;
import br.imd.mybookplace.entities.LivroFavorito;
import br.imd.mybookplace.entities.StatusLeitura;
import br.imd.mybookplace.services.LivroFavoritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller responsável por gerenciar os endpoints de livros favoritos dos usuários.
 */
@RestController
@RequestMapping("/api/livros-favoritos")
public class LivroFavoritoController {

    private final LivroFavoritoService livroFavoritoService;

    public LivroFavoritoController(LivroFavoritoService livroFavoritoService) {
        this.livroFavoritoService = livroFavoritoService;
    }

  /**
     * Adiciona um livro à lista de favoritos do usuário.
     *
     * @param userId ID do usuário.
     * @param title Título do livro.
     * @param author Autor do livro.
     * @param thumbnailUrl URL da imagem do livro.
     * @param isbn ISBN do livro.
     * @return ResponseEntity com o livro favorito criado e status 201.
     */  
  @PostMapping("/register-book/{userId}")
    public ResponseEntity<FavoriteBookDTO> adicionarLivroFavorito(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId,
            @RequestBody FavoriteBookDTO favoriteBookDTO
            ) {

            LivroFavorito livroFavorito = livroFavoritoService.adicionarLivroFavorito(
                userId,
                favoriteBookDTO.getTitle(),
                favoriteBookDTO.getAuthor(),
                favoriteBookDTO.getThumbnailUrl(),
                favoriteBookDTO.getIsbn()
                );

            var response = new FavoriteBookDTO(
                livroFavorito.getTitle(),
                livroFavorito.getAuthor(),
                livroFavorito.getThumbnailUrl(),
                livroFavorito.getIsbn(),
                livroFavorito.getStatusLeitura()
                );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Lista todos os livros favoritos de um usuário.
     *
     * @param userId ID do usuário.
     * @return ResponseEntity com a lista de livros favoritos do usuário.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteBookDTO>> listarFavoritosPorUsuario(
    @RequestHeader("Authorization") String authorizationHeader, 
    @PathVariable Long userId) {
       
            List<LivroFavorito> livrosFavoritos = livroFavoritoService.listarFavoritosPorUsuario(userId);

            List<FavoriteBookDTO> livrosFavoritosDTO = livrosFavoritos.stream()
                .map(livro -> new FavoriteBookDTO(
                    livro.getTitle(),
                    livro.getAuthor(),
                    livro.getThumbnailUrl(),
                    livro.getIsbn(),
                    livro.getStatusLeitura()))
                .toList();
            
            return ResponseEntity.ok(livrosFavoritosDTO);
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
        @PathVariable String isbn) {
            
        livroFavoritoService.removerLivroFavorito(userId, isbn);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos os livros favoritos do usuário agrupados por status de leitura.
     *
     * @param userId ID do usuário.
     * @return ResponseEntity com mapa de listas de favoritos agrupados por status.
     */
    @GetMapping("/agrupados/{userId}")
    public ResponseEntity<Map<StatusLeitura, List<FavoriteBookDTO>>> listarFavoritosAgrupadosPorStatus(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId) {

        Map<StatusLeitura, List<LivroFavorito>> agrupados = livroFavoritoService.listarFavoritosPorStatus(userId);

        Map<StatusLeitura, List<FavoriteBookDTO>> agrupadosDTO = agrupados.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream()
                        .map(livro -> new FavoriteBookDTO(
                                livro.getTitle(),
                                livro.getAuthor(),
                                livro.getThumbnailUrl(),
                                livro.getIsbn(),
                                livro.getStatusLeitura()
                        ))
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
    @PatchMapping("/{userId}/{isbn}/status")
    public ResponseEntity<Void> atualizarStatusLeitura(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId,
            @PathVariable String isbn,
            @RequestParam StatusLeitura statusLeitura) {

        livroFavoritoService.atualizarStatusLeitura(userId, isbn, statusLeitura);
        return ResponseEntity.noContent().build();
    }
}