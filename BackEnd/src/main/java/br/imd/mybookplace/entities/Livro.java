package br.imd.mybookplace.entities;

import java.util.List;

import br.imd.framework.entities.Produto;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "livros")
public class Livro extends Produto{
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String subtitle;
    private List<String> authors;
    private String editora;
    private String description;
    private List<String> categories;
    private String isbn;

    public Livro(String title, String thumbnailUrl, String subtitle, List<String> authors, String editora,
            String description, List<String> categories, String isbn) {
        super(title, thumbnailUrl);
        this.subtitle = subtitle;
        this.authors = authors;
        this.editora = editora;
        this.description = description;
        this.categories = categories;
        this.isbn = isbn;
    }

    public Livro(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return super.getTitle();
    }

    public void setTitle(String title) {
        super.setTitle(title);
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getThumbnailUrl() {
        return super.getThumbnailUrl();
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        super.setThumbnailUrl(thumbnailUrl);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
