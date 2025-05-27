package br.imd.mybookplace.entities;

import jakarta.persistence.*;

import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "livro_favorito", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "isbn"})
})
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

    public LivroFavorito(){};

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
