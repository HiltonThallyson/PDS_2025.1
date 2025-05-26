package br.imd.mybookplace.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.imd.mybookplace.DTOS.CreateUserDTO;
import br.imd.mybookplace.DTOS.LoginResponseDTO;
import br.imd.mybookplace.DTOS.LoginUserDTO;
import br.imd.mybookplace.services.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Controller responsável por autenticação de usuários: registro e login.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthService userService;


    /**
     * Realiza o registro de um novo usuário.
     *
     * @param createUserDTO Dados do usuário a ser criado.
     * @return ResponseEntity com status 201 (Created) em caso de sucesso.
     */
    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody @Validated CreateUserDTO createUserDTO) {
        userService.createUser(createUserDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Realiza o login do usuário e retorna o token JWT.
     *
     * @param loginUserDTO Dados de login do usuário.
     * @return ResponseEntity contendo o token JWT em caso de sucesso.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated LoginUserDTO loginUserDTO) {
        var token = userService.login(loginUserDTO);
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
