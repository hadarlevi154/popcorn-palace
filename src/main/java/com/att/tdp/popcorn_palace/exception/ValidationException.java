package com.att.tdp.popcorn_palace.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    private final String errorCode;

    public ValidationException(String message) {
        super(message);
        this.errorCode = null;
    }

    public ValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
