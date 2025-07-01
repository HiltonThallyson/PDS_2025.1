// package br.imd.mybookplace.repositories;

// import br.imd.framework.entities.User;
// import br.imd.mybookplace.entities.LivroFavorito;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

// import java.util.List;
// import java.util.Optional;

// @Repository
// public interface LivroFavoritoRepository extends JpaRepository<LivroFavorito, String> {
//     List<LivroFavorito> findByUser(User user);

//     Optional<LivroFavorito> findByUserAndIsbn(User user, String isbn);

//     void deleteByUserAndIsbn(User user, String isbn);
// }