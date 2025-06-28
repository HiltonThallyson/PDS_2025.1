package br.imd.framework.services;


import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.imd.framework.DTOs.CreateUserDTO;
import br.imd.framework.DTOs.LoginUserDTO;
import br.imd.framework.entities.User;
import br.imd.framework.entities.UserRole;
import br.imd.framework.repositories.UserRepository;
import br.imd.mybookplace.exceptions.AuthException;

@Service
public class AuthService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    
    
    /**
     * Cria um novo usuário no sistema com perfil padrão USER.
     *
     * @param createUserDTO DTO contendo username, senha, email e nickname do usuário.
     * @throws AuthException se ocorrer erro ao salvar o usuário no banco de dados.
     */
    public void createUser(CreateUserDTO createUserDTO) {
        try {
            var encryptedPassword  = new BCryptPasswordEncoder().encode(createUserDTO.getPassword());
            User user = new User(createUserDTO.getUsername(), encryptedPassword, createUserDTO.getEmail(), createUserDTO.getNickName(), UserRole.USER);
            userRepository.save(user);
        } catch (Exception e) {
            throw new AuthException("Erro ao criar usuário", e);
        }
    }

    /**
     * Realiza o login de um usuário, autenticando suas credenciais e gerando um token JWT.
     *
     * @param loginUserDTO DTO contendo username e senha do usuário.
     * @return Token JWT válido para o usuário autenticado.
     * @throws AuthException se as credenciais forem inválidas ou ocorrer erro na autenticação.
     */
    public String login(LoginUserDTO loginUserDTO) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword());
            var authentication = authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User)authentication.getPrincipal());
            return token;
        } catch (Exception e) {
            throw new AuthException("Usuário ou senha inválidos", e);
        }
    }

    /**
     * Busca um usuário pelo username para integração com o Spring Security.
     *
     * @param username Nome de usuário a ser buscado.
     * @return UserDetails do usuário encontrado.
     * @throws UsernameNotFoundException se o usuário não for encontrado.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    
     public User getUserFromToken(String token) {
        String username = tokenService.validateToken(token);
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }
}