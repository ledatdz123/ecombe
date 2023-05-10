package com.example.ecombe.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /*if you not found in the service controller then orElseThrow method throw here i will give response*/
    @ExceptionHandler(ResourceNotFoundException.class)
    public String HandleResourceNotFoundException(ResourceNotFoundException exception){
        return exception.getMessage();
    }

}
