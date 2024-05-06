package com.easysoftware.common.exception;

import java.io.Serial;

public class MyJacksonException extends RuntimeException {
    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for MyJacksonException with a message.
     *
     * @param message The exception message
     */
    public MyJacksonException(final String message) {
        super(message);
    }

    /**
     * Retrieves the exception message.
     *
     * @return The exception message
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
