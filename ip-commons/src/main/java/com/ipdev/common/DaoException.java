package com.ipdev.common;

public class DaoException extends IpdException {
    private static final long serialVersionUID = 1L;

    public DaoException(String message) {
        this(message, null);
    }

    public DaoException(String message, Throwable e) {
        super(message, e);
    }

}
