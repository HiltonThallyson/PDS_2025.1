package br.imd.framework.services;

import java.util.List;

import br.imd.framework.DTOs.ProdutoDTO;
import br.imd.framework.exceptions.ApiException;


public abstract class ProdutoService<T extends ProdutoDTO> {

    abstract public List<T> buscarPorNome(String titulo) throws ApiException;

    abstract public List<T> buscarPorQuantidade(int qtdPorCategoria)  throws ApiException;
}
