package br.imd.mybookplace.controllers;

import br.imd.framework.DTOs.CreateUserDTO;
import br.imd.framework.DTOs.LoginUserDTO;
import br.imd.framework.controllers.AuthenticationController;
import br.imd.framework.services.AuthService;
import br.imd.mybookplace.exceptions.AuthException;
import br.imd.mybookplace.exceptions.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(authenticationController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void createUser_DeveRetornarCreated_QuandoCadastroValido() throws Exception {
        // Este teste garante que o endpoint /api/auth/register retorna 201 (Created) quando o cadastro é realizado com sucesso.

        // Arrange
        CreateUserDTO dto = new CreateUserDTO("user", "senha", "email@email.com", "nick");
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user\",\"password\":\"senha\",\"email\":\"email@email.com\",\"nickName\":\"nick\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void createUser_DeveRetornarUnauthorized_QuandoServiceLancaAuthException() throws Exception {
        // Este teste garante que, se o service lançar AuthException, o endpoint retorna 401 (Unauthorized).
        
        // Arrange
        doThrow(new AuthException("erro")).when(authService).createUser(any(CreateUserDTO.class));
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user\",\"password\":\"senha\",\"email\":\"email@email.com\",\"nickName\":\"nick\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_DeveRetornarToken_QuandoLoginValido() throws Exception {
        // Este teste garante que o endpoint /api/auth/login retorna 200 (OK) e o token quando o login é realizado com sucesso.
        
        // Arrange
        when(authService.login(any(LoginUserDTO.class))).thenReturn("token123");
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user\",\"password\":\"senha\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"));
    }

    @Test
    void login_DeveRetornarUnauthorized_QuandoServiceLancaAuthException() throws Exception {
        // Este teste garante que, se o service lançar AuthException no login, o endpoint retorna 401 (Unauthorized).
        
        // Arrange
        doThrow(new AuthException("erro login")).when(authService).login(any(LoginUserDTO.class));
        
        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user\",\"password\":\"senha\"}"))
                .andExpect(status().isUnauthorized());
    }
}
