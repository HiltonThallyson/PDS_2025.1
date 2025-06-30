package br.imd.framework.exceptions;

public abstract class FavoritoException extends RuntimeException{
    public FavoritoException(String message){
        super (message);
    }

    public FavoritoException(String message, Throwable cause){
        super (message, cause);
    }
    
}
