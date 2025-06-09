package com.adminservices.admin_services.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exp){
        return new ResponseEntity<>(exp.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IsPresentException.class)
    public ResponseEntity<String> handleIsPresentException(IsPresentException exp){
        return new ResponseEntity<>(exp.getMessage(),HttpStatus.CONFLICT);
    }
}
