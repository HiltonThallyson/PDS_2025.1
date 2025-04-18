package br.imd.mybookplace.controllers;


import br.imd.mybookplace.DTOS.LivroDTO;
import br.imd.mybookplace.services.GoogleBookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/livros")
public class LivroController {
    public final GoogleBookService googleService;


    public LivroController(GoogleBookService googleService) {
        this.googleService = googleService;
    }

    @GetMapping("/titulo")
    public LivroDTO buscarLivroPorTitulo(@RequestParam String titulo){
        System.out.println(titulo);
        return googleService.buscarLivroPorTitulo(titulo);
    }

    @GetMapping ("/autor")
    public LivroDTO buscarLivroPorAutor(@RequestParam String autor){
        System.out.println(autor);
        return googleService.buscarLivroPorAutor(autor);
    }

}
