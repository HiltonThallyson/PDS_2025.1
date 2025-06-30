package br.imd.framework.entities;

public abstract class Produto {
    private String title;
    private String thumbnailUrl;

    public Produto(String title, String thumbnailUrl) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Produto() {}

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
