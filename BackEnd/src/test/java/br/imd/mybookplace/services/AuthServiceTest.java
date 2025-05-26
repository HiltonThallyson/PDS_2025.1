package br.imd.mybookplace.services;

import br.imd.mybookplace.DTOS.CreateUserDTO;
import br.imd.mybookplace.DTOS.LoginUserDTO;
import br.imd.mybookplace.entities.User;
import br.imd.mybookplace.entities.UserRole;
import br.imd.mybookplace.exceptions.AuthException;
import br.imd.mybookplace.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_DeveSalvarUsuario_QuandoDadosValidos() {
        // Este teste garante que o método salva o usuário sem lançar exceção, validando o fluxo normal de cadastro.

        // Arrange
        CreateUserDTO dto = new CreateUserDTO("user", "senha", "email@email.com", "nick");
        when(userRepository.save(any(User.class))).thenReturn(new User());
        // Act & Assert
        assertDoesNotThrow(() -> authService.createUser(dto));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_DeveLancarAuthException_QuandoRepositoryFalha() {
        // Este teste garante que uma exceção do repositório é encapsulada em AuthException, validando o tratamento de erro no cadastro.

        // Arrange
        CreateUserDTO dto = new CreateUserDTO("user", "senha", "email@email.com", "nick");
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("erro"));

        // Act & Assert
        assertThrows(AuthException.class, () -> authService.createUser(dto));
    }

    @Test
    void login_DeveRetornarToken_QuandoCredenciaisValidas() {
        // Este teste garante que retorna token quando autenticação e geração de token são bem-sucedidas, validando o fluxo normal de login.

        // Arrange
        LoginUserDTO dto = new LoginUserDTO("user", "senha");
        Authentication authentication = mock(Authentication.class);
        User user = new User("user", "senha", "email", "nick", UserRole.USER);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenReturn("token123");
        
        // Act
        String token = authService.login(dto);
        
        // Assert
        assertEquals("token123", token);
    }

    @Test
    void login_DeveLancarAuthException_QuandoCredenciaisInvalidas() {
        // Este teste garante que falha de autenticação lança AuthException, validando o tratamento de erro de login.
        
        // Arrange
        LoginUserDTO dto = new LoginUserDTO("user", "senha");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new BadCredentialsException("bad creds"));
        
        // Act & Assert
        assertThrows(AuthException.class, () -> authService.login(dto));
    }

    @Test
    void login_DeveLancarAuthException_QuandoTokenServiceFalha() {
        // Este teste garante que falha ao gerar token lança AuthException, validando o tratamento de erro no fluxo de login.
        
        // Arrange
        LoginUserDTO dto = new LoginUserDTO("user", "senha");
        Authentication authentication = mock(Authentication.class);
        User user = new User("user", "senha", "email", "nick", UserRole.USER);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(user)).thenThrow(new RuntimeException("erro token"));
        
        // Act & Assert
        assertThrows(AuthException.class, () -> authService.login(dto));
    }

    @Test
    void loadUserByUsername_DeveRetornarUsuario_QuandoEncontrado() {
        // Este teste garante que retorna o usuário quando encontrado, validando o fluxo normal de busca por username.
        
        // Arrange
        User user = new User();
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        
        // Act
        User result = (User) authService.loadUserByUsername("user");
        
        // Assert
        assertEquals(user, result);
    }

    @Test
    void loadUserByUsername_DeveLancarUsernameNotFoundException_QuandoNaoEncontrado() {
        // Este teste garante que lança UsernameNotFoundException quando usuário não existe, validando o tratamento de erro na busca por username.
        
        // Arrange
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername("user"));
    }
}
