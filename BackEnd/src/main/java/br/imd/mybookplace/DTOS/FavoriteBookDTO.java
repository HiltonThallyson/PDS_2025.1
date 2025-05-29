package br.imd.mybookplace.DTOS;

import br.imd.mybookplace.entities.StatusLeitura;

public class FavoriteBookDTO {
    String title;
    String author;
    String thumbnailUrl;
    String isbn;
    StatusLeitura statusLeitura;

    public FavoriteBookDTO(String title, String author, String thumbnailUrl, String isbn, StatusLeitura statusLeitura) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.isbn = isbn;
        this.statusLeitura = statusLeitura;
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
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public StatusLeitura getStatusLeitura() {
        return statusLeitura;
    }
    public void setStatusLeitura(StatusLeitura statusLeitura) {
        this.statusLeitura = statusLeitura;
    }
}
