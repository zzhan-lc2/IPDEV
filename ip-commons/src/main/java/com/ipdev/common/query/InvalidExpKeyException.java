package com.ipdev.common.query;

public class InvalidExpKeyException extends Exception {
    private static final long serialVersionUID = 1L;

    String invalidExpKey;

    public InvalidExpKeyException(String expKey, String message) {
        super(message);
        this.invalidExpKey = expKey;
    }

    public String getInvalidExpKey() {
        return this.invalidExpKey;
    }
}
