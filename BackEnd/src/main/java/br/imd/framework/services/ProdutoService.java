package br.imd.framework.services;

import java.util.List;

import br.imd.framework.entities.Produto;


public abstract class ProdutoService<T extends Produto> {

    abstract public List<T> buscarPorNome(String titulo);

    abstract public List<T> buscarPorQuantidade(int qtdPorCategoria);
}
