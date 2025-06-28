package br.imd.framework.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.imd.framework.DTOs.ProdutoDTO;
import br.imd.framework.services.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/nome")
    public List<ProdutoDTO> buscarProdutoPorNome(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String nome){

        System.out.println(nome);
        return produtoService.buscarPorNome(nome);
    }

    @GetMapping("/quantidade")
    public List<ProdutoDTO> buscarProdutosPorQuantidade(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name="qtdPorCategoria", required = false, defaultValue = "10") int qtdPorCategoria){
        System.out.println(qtdPorCategoria);
        return produtoService.buscarPorQuantidade(qtdPorCategoria);
    }
}
