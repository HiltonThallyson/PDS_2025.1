package br.imd.framework.exceptions;

public class ProdutoFavorito extends RuntimeException{
    public ProdutoFavorito(String message){
        super (message);
    }

    public ProdutoFavorito(String message, Throwable cause){
        super (message, cause);
    }
    
}
