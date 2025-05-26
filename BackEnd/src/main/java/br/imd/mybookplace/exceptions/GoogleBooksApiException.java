package br.imd.mybookplace.exceptions;

public class GoogleBooksApiException extends RuntimeException {
    public GoogleBooksApiException(String message) {
        super(message);
    }
    
    public GoogleBooksApiException(String message, Throwable cause) {
        super(message, cause);
    }
}