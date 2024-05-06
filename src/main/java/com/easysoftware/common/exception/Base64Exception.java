package com.easysoftware.common.exception;

import java.io.Serial;

public class Base64Exception extends RuntimeException {
    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for Base64Exception with a message.
     *
     * @param message The exception message
     */
    public Base64Exception(final String message) {
        super(message);
    }

    /**
     * Default constructor for Base64Exception.
     */
    public Base64Exception() {
    }

}
