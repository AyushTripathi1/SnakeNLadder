package com.kidex.SnakeNLadder.exception;

import java.io.Serializable;

public class DataAccessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1L;

    public DataAccessException() {
        super();
    }

    public DataAccessException(final String message) {
        super(message);
    }

    public DataAccessException(final Throwable cause) {
        super(cause);
    }

    public DataAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
