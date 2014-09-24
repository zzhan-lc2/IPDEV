package com.ipdev.common;

public class DaoNetworkException extends DaoException {
    private static final long serialVersionUID = 1L;

    public DaoNetworkException(String message) {
        super(message);
    }

    public DaoNetworkException(String message, Throwable e) {
        super(message, e);
    }

}
