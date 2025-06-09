package com.adminservices.admin_services.exceptionhandling;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
