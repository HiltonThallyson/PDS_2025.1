package br.imd.mybookplace.services;

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

import br.imd.mybookplace.DTOS.CreateUserDTO;
import br.imd.mybookplace.DTOS.LoginUserDTO;
import br.imd.mybookplace.entities.User;
import br.imd.mybookplace.entities.UserRole;
import br.imd.mybookplace.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    
    
    public void createUser(CreateUserDTO createUserDTO) {
        var encryptedPassword  = new BCryptPasswordEncoder().encode(createUserDTO.getPassword());
        User user = new User(createUserDTO.getUsername(), encryptedPassword, createUserDTO.getEmail(), createUserDTO.getNickName(), UserRole.USER);
        
        userRepository.save(user);
    }

    public String login(LoginUserDTO loginUserDTO) {
       var usernamePassword = new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword());
        
        var authentication = authenticationManager.authenticate(usernamePassword);
        
        var token = tokenService.generateToken((User)authentication.getPrincipal());


        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if(user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }
}