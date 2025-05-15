package br.imd.mybookplace.controllers;


import br.imd.mybookplace.DTOS.LivroDTO;
import br.imd.mybookplace.services.GoogleBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    public final GoogleBookService googleService;


    public LivroController(GoogleBookService googleService) {
        this.googleService = googleService;
    }

    @GetMapping("/titulo")
    public List<LivroDTO> buscarLivroPorTitulo(@RequestParam String titulo){
        System.out.println(titulo);
        return googleService.buscarLivrosPorTitulo(titulo);
    }

    @GetMapping ("/autor")
    public List<LivroDTO> buscarLivroPorAutor(@RequestParam String autor){
        System.out.println(autor);
        return googleService.buscarLivrosPorAutor(autor);
    }

    @GetMapping("/quantidade")
    public List<LivroDTO> buscarLivrosPorQuantidade(@RequestParam(name="qtdPorCategoria", required = false, defaultValue = "10") int qtdPorCategoria){
        System.out.println(qtdPorCategoria);
        return googleService.buscarLivrosPorQuantidade(qtdPorCategoria);
    }

    @GetMapping("/categoria")
    public List<LivroDTO> buscarLivrosPorCategoria(@RequestParam String categoria){
        System.out.println(categoria);
        return googleService.buscarLivrosPorCategoria(categoria);
    }

    @GetMapping("/isbn")
    public List<LivroDTO> buscarLivrosPorISBN(@RequestParam String isbn){
        System.out.println(isbn);
        return googleService.buscarLivrosPorISBN(isbn);
    }
}
