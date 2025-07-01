package br.imd.framework.entities;

public abstract class ProdutoFavorito {
    private String userID;
    private String title;
    private String thumbnailUrl;

    public ProdutoFavorito() {}

    public ProdutoFavorito(String userID, String title, String thumbnailUrl) {
        this.userID = userID;
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}