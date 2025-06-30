package br.imd.framework.exceptions;

public class ConnectTimeOutException extends RuntimeException {
    public ConnectTimeOutException(String message) {
        super(message);
    }

    public ConnectTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

}
