package com.washerservices.washer_services.exceptionhandling;

public class WasherNotFoundException extends RuntimeException {
    public WasherNotFoundException(String message) {
        super(message);
    }
}
