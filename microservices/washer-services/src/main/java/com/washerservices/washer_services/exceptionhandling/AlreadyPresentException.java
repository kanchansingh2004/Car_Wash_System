package com.washerservices.washer_services.exceptionhandling;

public class AlreadyPresentException extends RuntimeException {
    public AlreadyPresentException(String message) {
        super(message);
    }
}
