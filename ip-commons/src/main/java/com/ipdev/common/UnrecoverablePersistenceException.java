package com.ipdev.common;

public class UnrecoverablePersistenceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnrecoverablePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnrecoverablePersistenceException(String message) {
        super(message);
    }

    public UnrecoverablePersistenceException(Throwable cause) {
        super(cause);
    }
}
