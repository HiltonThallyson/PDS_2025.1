package br.imd.mymovieplace.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.imd.framework.controllers.BaseProdutoController;
import br.imd.mymovieplace.DTOS.FilmeDTO;
import br.imd.mymovieplace.services.FilmeService;

@RestController
@RequestMapping("/api/produtos")
public class FilmeController extends BaseProdutoController<FilmeDTO, FilmeService> {

    public FilmeController(FilmeService produtoService) {
        super(produtoService);
    }
}
