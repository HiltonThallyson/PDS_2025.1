package br.imd.framework.entities;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
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
    public void setTitle(String title) {
        this.title = title;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;   
    }
}
