package br.imd.mybookplace.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.imd.mybookplace.entities.LivroFavorito;
import br.imd.mybookplace.entities.User;
import br.imd.mybookplace.repositories.LivroFavoritoRepository;
import br.imd.mybookplace.repositories.UserRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class LivroFavoritoService {

    private LivroFavoritoRepository livroFavoritoRepository;
    private UserRepository userRepository;

    public LivroFavoritoService(LivroFavoritoRepository livroFavoritoRepository, UserRepository userRepository){
        this.livroFavoritoRepository = livroFavoritoRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly=true)
    public List<LivroFavorito> listarFavoritosPorUsuario(Long userId){
        User user = buscarUserPorID(userId);

        return livroFavoritoRepository.findByUser(user);
    }

    @Transactional
    public LivroFavorito adicionarLivroFavorito(Long userId, String title, String author, String thumbnailUrl, String isbn){
        
        User user = buscarUserPorID(userId);

        Optional<LivroFavorito> livroExistente = livroFavoritoRepository.findByUserAndIsbn(user, isbn);

        if(livroExistente.isPresent()){
            throw new IllegalArgumentException("Livro já existe na lista de favoritos");
        }

        LivroFavorito livroFavorito = criarLivroFavorito(user, title, author, thumbnailUrl, isbn);
        
        return livroFavoritoRepository.save(livroFavorito);
    }

    @Transactional
    public void removerLivroFavorito(Long userId, String isbn){
        User user = buscarUserPorID(userId);

        LivroFavorito livroFavorito = livroFavoritoRepository.findByUserAndIsbn(user, isbn)
            .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado na lista de favoritos"));

        livroFavoritoRepository.delete(livroFavorito);
    }

    private User buscarUserPorID(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID: " + userId));
        
        return user;
    }

    private LivroFavorito criarLivroFavorito(User user, String title, String author, String thumbnailUrl, String isbn) {
        return new LivroFavorito(user, title, author, isbn, thumbnailUrl);
    }
}
