package br.imd.mybookplace.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno do servidor. Por favor, tente novamente mais tarde");
    }

    @ExceptionHandler(GoogleBooksApiException.class)
    public ResponseEntity<String> handleGoogleBooksApiException(GoogleBooksApiException e){
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body("Não foi possível obter informações dos livros no momento. Por favor, tente novamente mais tarde.");
    }

    @ExceptionHandler(LivroFavoritoException.class)
    public ResponseEntity<String> handleLivroFavoritoException (LivroFavoritoException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(LLMServiceException.class)
    public ResponseEntity<String> handleLLMServiceException(LLMServiceException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato numérico inválido fornecido: " + e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleAuthException(AuthException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<String> handleTokenException(TokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

}
