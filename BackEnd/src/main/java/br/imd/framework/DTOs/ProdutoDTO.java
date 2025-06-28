package br.imd.framework.DTOs;

import br.imd.framework.enums.ProdutoStatus;

public abstract class ProdutoDTO {
    private String title;
    private String thumbnailUrl;
    private ProdutoStatus status;

    public ProdutoDTO(String title, String thumbnailUrl, ProdutoStatus status) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public ProdutoStatus getStatus() {
        return status;
    }
    public void setStatus(ProdutoStatus status) {
        this.status = status;
    }
}
