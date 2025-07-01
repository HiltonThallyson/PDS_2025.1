package br.imd.framework.exceptions;

public class LivroFavoritoException extends RuntimeException{
    public LivroFavoritoException(String message){
        super (message);
    }

    public LivroFavoritoException(String message, Throwable cause){
        super (message, cause);
    }
    
}
