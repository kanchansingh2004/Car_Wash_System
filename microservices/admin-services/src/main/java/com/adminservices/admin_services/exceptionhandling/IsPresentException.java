package com.adminservices.admin_services.exceptionhandling;

public class IsPresentException extends RuntimeException {
    public IsPresentException(String message) {
        super(message);
    }
}
