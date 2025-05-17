package br.imd.mybookplace.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "livro_favorito", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "isbn"})
})
@NoArgsConstructor
@Getter
@Setter
public class LivroFavorito {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "isbn", nullable = false)
    private String isbn;

    //informacoes para exibicao rapida
    private String title;
    private String author;
    private String thumbnailUrl;

    public LivroFavorito(User user, String title, String author, String isbn, String thumbnailUrl) {
        this.user = user;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.thumbnailUrl = thumbnailUrl;
    }
}
