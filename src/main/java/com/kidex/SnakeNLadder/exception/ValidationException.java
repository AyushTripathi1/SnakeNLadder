package com.kidex.SnakeNLadder.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class ValidationException extends Exception {

    private static final long serialVersionUID = 5165575116609157375L;

    /**
     *
     */

    public ValidationException(final String message) {
        super(message);
    }

}

