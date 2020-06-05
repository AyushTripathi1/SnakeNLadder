package com.kidex.SnakeNLadder.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ServiceResponse<T> extends ResponseEntity<T> {

    public ServiceResponse(final T data) {
        super(data, HttpStatus.OK);
    }

    public ServiceResponse(final T data, final HttpStatus status) {
        super(data, status);
    }

}
