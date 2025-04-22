package br.imd.mybookplace.DTOS;

public class LoginResponseDTO {
    String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
