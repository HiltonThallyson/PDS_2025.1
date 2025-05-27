package br.imd.mybookplace.controllers;

import br.imd.mybookplace.DTOS.FavoriteBookDTO;
import br.imd.mybookplace.entities.LivroFavorito;
import br.imd.mybookplace.services.LivroFavoritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livros-favoritos")
public class LivroFavoritoController {

    private final LivroFavoritoService livroFavoritoService;

    public LivroFavoritoController(LivroFavoritoService livroFavoritoService) {
        this.livroFavoritoService = livroFavoritoService;
    }

    @PostMapping("/register-book/{userId}")
    public ResponseEntity<FavoriteBookDTO> adicionarLivroFavorito(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long userId,
            @RequestBody FavoriteBookDTO favoriteBookDTO
            ) {
        try{
            LivroFavorito livroFavorito = livroFavoritoService.adicionarLivroFavorito(userId, favoriteBookDTO.getTitle(), favoriteBookDTO.getAuthor(), favoriteBookDTO.getThumbnailUrl(), favoriteBookDTO.getIsbn());
            var response = new FavoriteBookDTO(livroFavorito.getTitle(), livroFavorito.getAuthor(), livroFavorito.getThumbnailUrl(), livroFavorito.getIsbn());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoriteBookDTO>> listarFavoritosPorUsuario(
    @RequestHeader("Authorization") String authorizationHeader, 
    @PathVariable Long userId) {
        try{
            List<LivroFavorito> livrosFavoritos = livroFavoritoService.listarFavoritosPorUsuario(userId);
            List<FavoriteBookDTO> livrosFavoritosDTO = livrosFavoritos.stream()
                .map(livro -> new FavoriteBookDTO(livro.getTitle(), livro.getAuthor(), livro.getThumbnailUrl(), livro.getIsbn()))
                .toList();
            
            return ResponseEntity.ok(livrosFavoritosDTO);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{userId}/{isbn}")
    public ResponseEntity<Void> removerLivroFavorito(
    @RequestHeader("Authorization") String authorizationHeader, 
    @PathVariable Long userId, 
    @PathVariable String isbn) {
        try{
            livroFavoritoService.removerLivroFavorito(userId, isbn);
            return ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}