package br.imd.mybookplace.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.imd.mybookplace.DTOS.CreateUserDTO;
import br.imd.mybookplace.DTOS.LoginResponseDTO;
import br.imd.mybookplace.DTOS.LoginUserDTO;
import br.imd.mybookplace.services.AuthorizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthorizationService userService;


    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody @Validated CreateUserDTO createUserDTO) {
        try {
            if (userService.loadUserByUsername(createUserDTO.getUsername()) != null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (UsernameNotFoundException e) {
            userService.createUser(createUserDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginUserDTO loginUserDTO) {
        var token = userService.login(loginUserDTO);
        if(token == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    
    
}
