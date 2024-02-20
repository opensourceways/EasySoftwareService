package com.easysoftware.exception;

public class ParamErrorException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ParamErrorException(String message) {
        super(message);
    }
    public ParamErrorException() {
    }
}
