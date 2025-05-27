package br.imd.mybookplace.controllers;

import br.imd.mybookplace.entities.LivroFavorito;
import br.imd.mybookplace.services.LivroFavoritoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsável por gerenciar os endpoints de livros favoritos dos usuários.
 */
@RestController
@RequestMapping("/livros-favoritos")
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

    /**
     * Lista todos os livros favoritos de um usuário.
     *
     * @param userId ID do usuário.
     * @return ResponseEntity com a lista de livros favoritos do usuário.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<LivroFavorito>> listarFavoritosPorUsuario(@PathVariable String userId) {
        List<LivroFavorito> livrosFavoritos = livroFavoritoService.listarFavoritosPorUsuario(userId);
        return ResponseEntity.ok(livrosFavoritos);
    }

    /**
     * Remove um livro da lista de favoritos do usuário.
     *
     * @param userId ID do usuário.
     * @param isbn ISBN do livro a ser removido.
     * @return ResponseEntity com status 204 (No Content) em caso de sucesso.
     */
    @DeleteMapping("/{userId}/{isbn}")
    public ResponseEntity<Void> removerLivroFavorito(@PathVariable String userId, @PathVariable String isbn) {
        livroFavoritoService.removerLivroFavorito(userId, isbn);
        return ResponseEntity.noContent().build();
    }
}