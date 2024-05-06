package com.easysoftware.common.exception;

import java.io.Serial;

public class ParamErrorException extends RuntimeException {
    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for ParamErrorException with a message.
     *
     * @param message The exception message
     */
    public ParamErrorException(final String message) {
        super(message);
    }

    /**
     * Default constructor for ParamErrorException.
     */
    public ParamErrorException() {
    }

}
