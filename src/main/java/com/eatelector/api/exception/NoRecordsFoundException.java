package com.eatelector.api.exception;

public class NoRecordsFoundException extends RuntimeException {

    public NoRecordsFoundException(String message) {
        super(message);
    }

}
