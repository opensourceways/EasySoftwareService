package com.easysoftware.domain.common.exception;

public class EnumValidException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EnumValidException(String message) {
        super(message);
    }
    public EnumValidException() {
    }
}
