package com.easysoftware.exception;

public class MyJacksonException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MyJacksonException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
