package com.easysoftware.common.exception;

import java.io.Serial;

public class AppPkgIconException extends RuntimeException {
    /**
     * Serial version UID for serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for AppPkgIconException with a message.
     *
     * @param message The exception message
     */
    public AppPkgIconException(final String message) {
        super(message);
    }

    /**
     * Default constructor for AppPkgIconException.
     */
    public AppPkgIconException() {
    }

}
