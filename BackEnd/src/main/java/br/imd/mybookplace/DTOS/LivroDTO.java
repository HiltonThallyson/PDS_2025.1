package br.imd.mybookplace.DTOS;

import java.util.List;

import br.imd.framework.DTOs.ProdutoDTO;

public class LivroDTO extends ProdutoDTO {

    private List<String> authors;
    private String editora;
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }


}
