package br.imd.mybookplace.controllers;

import br.imd.mybookplace.entities.LivroFavorito;
import br.imd.mybookplace.services.LivroFavoritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros-favoritos")
public class LivroFavoritoController {

    private final LivroFavoritoService livroFavoritoService;

    public LivroFavoritoController(LivroFavoritoService livroFavoritoService) {
        this.livroFavoritoService = livroFavoritoService;
    }

    @PostMapping
    public ResponseEntity<LivroFavorito> adicionarLivroFavorito(
            @RequestParam String userId,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam String thumbnailUrl,
            @RequestParam String isbn) {
        LivroFavorito livroFavorito = livroFavoritoService.adicionarLivroFavorito(userId, title, author, thumbnailUrl, isbn);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroFavorito);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<LivroFavorito>> listarFavoritosPorUsuario(@PathVariable String userId) {
        List<LivroFavorito> livrosFavoritos = livroFavoritoService.listarFavoritosPorUsuario(userId);
        return ResponseEntity.ok(livrosFavoritos);
    }

    @DeleteMapping("/{userId}/{isbn}")
    public ResponseEntity<Void> removerLivroFavorito(@PathVariable String userId, @PathVariable String isbn) {
        livroFavoritoService.removerLivroFavorito(userId, isbn);
        return ResponseEntity.noContent().build();
    }
}