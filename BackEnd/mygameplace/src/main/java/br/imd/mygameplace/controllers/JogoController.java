package br.imd.mygameplace.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.imd.framework.controllers.BaseProdutoController;
import br.imd.mygameplace.DTOS.JogoDTO;
import br.imd.mygameplace.services.JogoService;

@RestController
@RequestMapping("/api/produtos")
public class JogoController extends BaseProdutoController<JogoDTO, JogoService> {

    public JogoController(JogoService produtoService) {
        super(produtoService);
    }
}
