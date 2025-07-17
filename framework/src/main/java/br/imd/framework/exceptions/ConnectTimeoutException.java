package br.imd.framework.exceptions;

public class ConnectTimeoutException extends RuntimeException {
    public ConnectTimeoutException(String message) {
        super(message);
    }

    public ConnectTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}