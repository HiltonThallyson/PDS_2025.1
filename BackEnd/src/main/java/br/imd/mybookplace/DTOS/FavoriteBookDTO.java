package br.imd.mybookplace.DTOS;

public class FavoriteBookDTO {
    String title;
    String author;
    String thumbnailUrl;
    String isbn;

    public FavoriteBookDTO(String title, String author, String thumbnailUrl, String isbn) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
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
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
