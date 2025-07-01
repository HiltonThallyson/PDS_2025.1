package br.imd.framework.DTOs;

import java.util.List;

public abstract class ProdutoDTO {
    private String title;
    private String subtitle;
    private String thumbnail;
    private String description;
    private List<String> categories;

    public ProdutoDTO(String title, String subtitle, String thumbnail, String description, List<String> categories) {
        this.title = title;
        this.subtitle = subtitle;
        this.thumbnail = thumbnail;
        this.description = description;
        this.categories = categories;
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
    
    public String getSubtitle() {
        return subtitle;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}