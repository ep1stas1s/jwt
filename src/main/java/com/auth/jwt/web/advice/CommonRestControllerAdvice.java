package com.auth.jwt.web.advice;

import com.auth.jwt.web.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonRestControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleAuthException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(e.getMessage()));
    }
}
