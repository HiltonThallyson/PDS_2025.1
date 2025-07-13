package br.imd.mygameplace.DTOS;

public class FavoriteGameResponseDTO {
    String title;
    String desenvolvedores;
    String thumbnailUrl;
    String isbn;

    public FavoriteGameResponseDTO(String title, String desenvolvedores, String thumbnailUrl, String isbn) {
        this.title = title;
        this.desenvolvedores = desenvolvedores;
        this.thumbnailUrl = thumbnailUrl;
        this.isbn = isbn;
    }
    public String getTitle() {
        return title;
    }
    public String getdesenvolvedores() {
        return desenvolvedores;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public String getIsbn() {
        return isbn;
    }
    
}
