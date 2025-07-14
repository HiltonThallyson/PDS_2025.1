package br.imd.mymovieplace.DTOS;

import java.util.List;

import br.imd.framework.DTOs.ProdutoDTO;

public class FilmeDTO extends ProdutoDTO {
    private List<String> criadores;
    private String publisher;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<String> getCriadores() {
        return criadores;
    }

    public void setCriadores(List<String> criadores) {
        this.criadores = criadores;
    }
}
