package br.imd.mybookplace.controllers;

import br.imd.mybookplace.DTOS.LLMRequestDTO;
import br.imd.mybookplace.DTOS.OfferDTO;
import br.imd.mybookplace.exceptions.GlobalExceptionHandler;
import br.imd.mybookplace.exceptions.LLMServiceException;
import br.imd.mybookplace.services.LLMService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LLMControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LLMService llmService;

    @InjectMocks
    private LLMController llmController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(llmController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void searchOffers_DeveRetornarListaVazia_QuandoServiceRetornaVazio() throws Exception {
        // Este teste garante que o endpoint /api/llm/search_price retorna uma lista vazia quando o service retorna uma lista vazia, validando o fluxo normal sem erros.
        
        // Arrange
        when(llmService.searchOffers(any(LLMRequestDTO.class))).thenReturn(Collections.emptyList());
        
        // Act & Assert
        mockMvc.perform(post("/api/llm/search_price")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"prompt\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void searchOffers_DeveRetornarListaComOfertas_QuandoServiceRetornaOfertas() throws Exception {
        // Este teste garante que o endpoint /api/llm/search_price retorna corretamente uma lista de ofertas quando o service retorna uma lista não vazia, validando o fluxo normal de sucesso.
        
        // Arrange
        OfferDTO oferta = new OfferDTO();
        when(llmService.searchOffers(any(LLMRequestDTO.class))).thenReturn(Collections.singletonList(oferta));
        
        // Act & Assert
        mockMvc.perform(post("/api/llm/search_price")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"prompt\":\"test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void searchOffers_DeveRetornarBadRequest_QuandoServiceLancaLLMServiceException() throws Exception {
        // Este teste garante que, se o service lançar LLMServiceException, o GlobalExceptionHandler responde com BadRequest (HTTP 400) e a mensagem de erro, validando o tratamento global de exceções de negócio.
        
        // Arrange
        String errorMessage = "Erro simulado ao buscar ofertas";
        when(llmService.searchOffers(any(LLMRequestDTO.class)))
                .thenThrow(new LLMServiceException(errorMessage));
        
                // Act & Assert
        mockMvc.perform(post("/api/llm/search_price")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"prompt\":\"test\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }

    @Test
    void generateImage_DeveRetornarString_QuandoServiceRetornaString() throws Exception {
        // Este teste garante que o endpoint /api/llm/generate_image_by_text retorna a string gerada pelo service quando o fluxo ocorre normalmente, validando o retorno esperado em caso de sucesso.
        
        // Arrange
        when(llmService.createImage(any(LLMRequestDTO.class))).thenReturn("imagem_base64");
        
        // Act & Assert
        mockMvc.perform(post("/api/llm/generate_image_by_text")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"prompt\":\"test image\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("imagem_base64"));
    }

    @Test
    void generateImage_DeveRetornarBadRequest_QuandoServiceLancaLLMServiceException() throws Exception {
        // Este teste garante que, se o service lançar LLMServiceException ao gerar imagem, o GlobalExceptionHandler responde com BadRequest (HTTP 400) e a mensagem de erro, validando o tratamento global de exceções de negócio.
        
        // Arrange
        String errorMessage = "Erro simulado ao gerar imagem";
        when(llmService.createImage(any(LLMRequestDTO.class)))
                .thenThrow(new LLMServiceException(errorMessage));
        
        // Act & Assert
        mockMvc.perform(post("/api/llm/generate_image_by_text")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"prompt\":\"test image\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }
}