package com.easysoftware.common.exception;

public class Base64Exception extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public Base64Exception(String message) {
        super(message);
    }
    public Base64Exception() {
    }
}
