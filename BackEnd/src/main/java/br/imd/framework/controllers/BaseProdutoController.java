package br.imd.framework.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.imd.framework.DTOs.ProdutoDTO;
import br.imd.framework.services.ProdutoService;

public abstract class BaseProdutoController<T extends ProdutoDTO, S extends ProdutoService<T>> {

    protected final S produtoService;

    public BaseProdutoController(S produtoService) {
        this.produtoService = produtoService;
    }

    protected ResponseEntity<List<T>> buscarProdutoPorNome(String nome) {
        List<T> resultados = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(resultados);
    }

    protected ResponseEntity<List<T>> buscarProdutosPorQuantidade(int qtdPorCategoria) {
        List<T> resultados = produtoService.buscarPorQuantidade(qtdPorCategoria);
        return ResponseEntity.ok(resultados);
    }
    
}
