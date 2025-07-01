package br.imd.framework.DTOs;

public abstract class ProdutoDTO {
    private String title;
    private String thumbnail;

    public ProdutoDTO(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public ProdutoDTO() {}

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    

    public void setTitle(String title) {
        this.title = title;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
