package com.easysoftware.common.exception;

import java.io.Serial;

public class NoneResException extends RuntimeException {
    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for NoneResException with a message.
     *
     * @param message The exception message
     */
    public NoneResException(final String message) {
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
