package com.easysoftware.common.exception;

import java.io.Serial;

public class AuthException extends RuntimeException {
    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for AuthException with a message.
     *
     * @param message The exception message
     */
    public AuthException(final String message) {
        super(message);
    }

    /**
     * Default constructor for AuthException.
     */
    public AuthException() {
    }

}