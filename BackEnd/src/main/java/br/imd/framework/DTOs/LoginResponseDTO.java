package br.imd.framework.DTOs;

public class LoginResponseDTO {
    String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
