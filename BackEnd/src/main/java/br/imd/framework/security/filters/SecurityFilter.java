package br.imd.framework.security.filters;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.imd.framework.entities.User;
import br.imd.framework.repositories.UserRepository;
import br.imd.framework.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    // @Override
    // protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    //         throws ServletException, IOException {
        
    //     var token = this.recoverToken(request);
    //     if(token!=null) {
    //         var username = tokenService.validateToken(token);
    //         Optional<UserDetails> user = userRepository.findByUsername(username);

    //         if(user.isPresent()) {
    //             var myUser = user.get();
                
    //             var authorization = new UsernamePasswordAuthenticationToken(myUser, null, myUser.getAuthorities());
    //             SecurityContextHolder.getContext().setAuthentication(authorization);
    //         }
            
            
    //     }
    //     filterChain.doFilter(request, response);
    // }
    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    var token = this.recoverToken(request);
    if (token != null) {
        try {
            var username = tokenService.validateToken(token);

            if (username != null) {
                Optional<User> user = userRepository.findByUsername(username);

                if (user.isPresent()) {
                    var myUser = user.get();
                    var authentication = new UsernamePasswordAuthenticationToken(myUser, null, myUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            // Ignora erro de token inválido para não bloquear rotas públicas
        }
    }

    filterChain.doFilter(request, response);
}


    private String recoverToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null) {
            return null;
        }

        return authorizationHeader.replace("Bearer ", "");
    }
    
}
