package com.easysoftware.common.exception;

import java.io.Serial;

public class EnumValidException extends RuntimeException {

    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for EnumValidException with a message.
     *
     * @param message The exception message
     */
    public EnumValidException(final String message) {
        super(message);
    }

    /**
     * Default constructor for EnumValidException.
     */
    public EnumValidException() {
    }

}
