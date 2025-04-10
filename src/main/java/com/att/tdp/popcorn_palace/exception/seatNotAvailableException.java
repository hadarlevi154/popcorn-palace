package com.att.tdp.popcorn_palace.exception;

public class seatNotAvailableException extends RuntimeException {

    public seatNotAvailableException(String message) {
        super(message);
    }

    public seatNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
