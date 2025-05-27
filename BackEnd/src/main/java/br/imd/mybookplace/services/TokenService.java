package br.imd.mybookplace.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.imd.mybookplace.entities.User;
import br.imd.mybookplace.exceptions.TokenException;

/**
 * Serviço responsável por operações relacionadas a tokens JWT, como geração e validação.
 */
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token JWT para o usuário autenticado.
     *
     * @param user Objeto de usuário autenticado.
     * @return Token JWT gerado.
     * @throws TokenException em caso de erro ao gerar o token.
     */

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("MyBookPlace-API")
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() +  86400000)) // 24 hours expiration
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new TokenException("Erro ao gerar token", exception);
        }
    }

    /**
     * Valida um token JWT recebido e retorna o username extraído do token se válido.
     *
     * @param token Token JWT a ser validado.
     * @return Username extraído do token se válido.
     * @throws TokenException em caso de token inválido ou erro na validação.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("MyBookPlace-API")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new TokenException("Token inválido", exception);
        }
    }
}
