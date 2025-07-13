package br.imd.mymovieplace.entities;

import java.util.List;

import br.imd.framework.entities.Produto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "filmes")
public class Filme extends Produto{
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String subtitle;
    private List<String> desenvolvedores;
    private String editora;
    private String description;
    private List<String> categories;
    private String isbn;

    public Filme(String title, String thumbnailUrl, String subtitle, List<String> desenvolvedores, String editora,
            String description, List<String> categories, String isbn) {
        super(title, thumbnailUrl);
        this.subtitle = subtitle;
        this.desenvolvedores = desenvolvedores;
        this.editora = editora;
        this.description = description;
        this.categories = categories;
        this.isbn = isbn;
    }

    public Filme(){};

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

    public List<String> getDevelopers() {
        return desenvolvedores;
    }

    public void setDevelopers(List<String> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
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
