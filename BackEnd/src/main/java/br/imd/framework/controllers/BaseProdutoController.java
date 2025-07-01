package br.imd.framework.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.imd.framework.DTOs.ProdutoDTO;
import br.imd.framework.services.ProdutoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/produtos")
public abstract class BaseProdutoController<T extends ProdutoDTO, S extends ProdutoService<T>> {

    protected final S produtoService;

    public BaseProdutoController(S produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/nome")
    protected ResponseEntity<List<T>> buscarProdutoPorNome(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String nome) {
        List<T> resultados = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/quantidade")
    protected ResponseEntity<List<T>> buscarProdutosPorQuantidade(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name="qtdPorCategoria", required = false, defaultValue = "10") int qtdPorCategoria) {
        List<T> resultados = produtoService.buscarPorQuantidade(qtdPorCategoria);
        return ResponseEntity.ok(resultados);
    }
    
}
