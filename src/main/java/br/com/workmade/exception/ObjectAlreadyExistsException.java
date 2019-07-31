package br.com.workmade.exception;

import java.io.Serializable;

public class ObjectAlreadyExistsException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public ObjectAlreadyExistsException(String message) {
        super(message);
    }

    public ObjectAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
