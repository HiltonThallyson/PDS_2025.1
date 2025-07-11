package br.imd.mybookplace.DTOS;

public class FavoriteBookResponseDTO {
    String title;
    String author;
    String thumbnailUrl;
    String isbn;

    public FavoriteBookResponseDTO(String title, String author, String thumbnailUrl, String isbn) {
        this.title = title;
        this.author = author;
        this.thumbnailUrl = thumbnailUrl;
        this.isbn = isbn;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public String getIsbn() {
        return isbn;
    }
    
}
