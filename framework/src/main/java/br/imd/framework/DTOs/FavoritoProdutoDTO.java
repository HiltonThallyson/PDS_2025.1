package br.imd.framework.DTOs;

public abstract class FavoritoProdutoDTO {
    private String title;
    private String thumbnailUrl;

    public FavoritoProdutoDTO(String title, String thumbnailUrl) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

}
