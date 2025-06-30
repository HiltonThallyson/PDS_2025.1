package br.imd.framework.controllers;

import java.util.List;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.imd.framework.entities.Produto;
import br.imd.framework.services.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
@ConditionalOnBean(ProdutoService.class)
public class ProdutoController <T extends Produto>{
    private final ProdutoService<T> produtoService;

    public ProdutoController(ProdutoService<T> produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/nome")
    public ResponseEntity<List<T>> buscarProdutoPorNome(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String nome){

        System.out.println(nome);
        return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    }

    @GetMapping("/quantidade")
    public ResponseEntity<List<T>> buscarProdutosPorQuantidade(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name="qtdPorCategoria", required = false, defaultValue = "10") int qtdPorCategoria){
        System.out.println(qtdPorCategoria);
        return ResponseEntity.ok(produtoService.buscarPorQuantidade(qtdPorCategoria));
    }
}
