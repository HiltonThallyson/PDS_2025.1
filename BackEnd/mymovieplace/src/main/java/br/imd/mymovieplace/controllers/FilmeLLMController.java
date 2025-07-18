package br.imd.mymovieplace.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.imd.framework.controllers.BaseLLMController;
import br.imd.mymovieplace.DTOS.MovieLLMRequestDTO;
import br.imd.mymovieplace.services.LLMServiceFilme;


@RestController
@RequestMapping("/api/llm")
public class FilmeLLMController extends BaseLLMController<MovieLLMRequestDTO, LLMServiceFilme> {

    public FilmeLLMController(LLMServiceFilme llmService) {
        super(llmService);
    }
}
