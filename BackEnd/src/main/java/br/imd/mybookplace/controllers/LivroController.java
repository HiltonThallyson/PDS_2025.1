package br.imd.mybookplace.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.imd.framework.controllers.BaseProdutoController;
import br.imd.mybookplace.DTOS.LivroDTO;
import br.imd.mybookplace.services.LivroService;

@RestController
@RequestMapping("/api/produtos")
public class LivroController extends BaseProdutoController<LivroDTO, LivroService> {

    public LivroController(LivroService produtoService) {
        super(produtoService);
    }

    // public ResponseEntity<List<LivroDTO>> buscarProdutoPorNome(@RequestHeader("Authorization") String authorizationHeader, @RequestParam String nome){
    //     System.out.println(nome);
    //     return ResponseEntity.ok(produtoService.buscarPorNome(nome));
    // }

    // public ResponseEntity<List<LivroDTO>> buscarProdutosPorQuantidade(@RequestHeader("Authorization") String authorizationHeader, @RequestParam(name="qtdPorCategoria", required = false, defaultValue = "10") int qtdPorCategoria){
    //     System.out.println(qtdPorCategoria);
    //     return ResponseEntity.ok(produtoService.buscarPorQuantidade(qtdPorCategoria));
    // }

    
}
