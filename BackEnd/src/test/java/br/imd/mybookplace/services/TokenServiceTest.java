package br.imd.mybookplace.services;

import br.imd.mybookplace.entities.User;
import br.imd.mybookplace.exceptions.TokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        // Define o valor do secret manualmente para os testes
        ReflectionTestUtils.setField(tokenService, "secret", "test-secret");
    }

    @Test
    void generateToken_DeveGerarToken_QuandoUsuarioValido() {
        // Este teste garante que o método gera um token JWT válido quando recebe um usuário válido.
        
        // Arrange
        User user = new User();
        user.setUsername("usuarioTeste");
        
        // Act
        String token = tokenService.generateToken(user);
        
        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void generateToken_DeveLancarTokenException_QuandoJWTCreationFalha() {
        // Este teste garante que, se ocorrer um erro ao criar o token (ex: secret nulo), o método lança TokenException.
        
        // Arrange
        ReflectionTestUtils.setField(tokenService, "secret", null);
        User user = new User();
        user.setUsername("usuarioTeste");
        
        // Act & Assert
        assertThrows(TokenException.class, () -> tokenService.generateToken(user));
    }

    @Test
    void validateToken_DeveLancarTokenException_QuandoJWTVerificationFalha() {
        // Este teste garante que, se o token for inválido, o método lança TokenException ao tentar validar.
        
        // Arrange
        String tokenInvalido = "tokenInvalido";
        
        // Act & Assert
        assertThrows(TokenException.class, () -> tokenService.validateToken(tokenInvalido));
    }
}