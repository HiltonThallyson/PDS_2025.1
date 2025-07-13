package br.imd.mygameplace.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.imd.framework.controllers.BaseLLMController;
import br.imd.mygameplace.services.LLMServiceJogo;


@RestController
@RequestMapping("/api/llm")
public class JogoLLMController extends BaseLLMController<LLMServiceJogo> {

    public JogoLLMController(LLMServiceJogo llmService) {
        super(llmService);
    }
}
