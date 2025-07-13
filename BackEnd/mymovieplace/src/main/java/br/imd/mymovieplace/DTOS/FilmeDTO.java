package br.imd.mymovieplace.DTOS;

import java.util.List;

import br.imd.framework.DTOs.ProdutoDTO;

public class FilmeDTO extends ProdutoDTO {
    private List<String> desenvolvedores;
    private String editora;

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public List<String> getDevelopers() {
        return desenvolvedores;
    }

    public void setDevelopers(List<String> desenvolvedores) {
        this.desenvolvedores = desenvolvedores;
    }


}
