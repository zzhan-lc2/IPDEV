package com.ipdev.common;

public class IpdException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public IpdException(String message) {
        super(message);
    }

    public IpdException(Throwable e) {
        super(e);
    }

    public IpdException(String message, Throwable e) {
        super(message, e);
    }
    // TODO
}
