package br.imd.framework.services;

import java.util.List;

import br.imd.framework.DTOs.ProdutoDTO;


public abstract class ProdutoService<T extends ProdutoDTO> {

    abstract public List<T> buscarPorNome(String titulo);

    abstract public List<T> buscarPorQuantidade(int qtdPorCategoria);
}
