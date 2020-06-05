package com.kidex.SnakeNLadder.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * User Defined Exception for any Repository failed action like saving or updating object
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
@NoArgsConstructor
public class SavingDataException extends Exception {

    private static final long serialVersionUID = -7084694588527379663L;

    public SavingDataException(final String message) {
        super(message);
    }
}
