package com.ipdev.common;

public class ServiceInternalException extends IpdException {
    private static final long serialVersionUID = 1L;

    public ServiceInternalException(String message) {
        super(message);
    }

    public ServiceInternalException(String message, Throwable e) {
        super(message, e);
    }

}
