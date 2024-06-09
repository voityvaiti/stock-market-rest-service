package com.myproject.stockmarketrestservice.exception.handler;

import com.mongodb.MongoWriteException;
import com.myproject.stockmarketrestservice.exception.ResourceNotFoundException;
import com.myproject.stockmarketrestservice.exception.UniqueConstraintsViolation;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, UniqueConstraintsViolation.class})
    public ResponseEntity<ErrorDetailsDto> handleValidationException(BindException ex) {

        String message = ex.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" "));

        return new ResponseEntity<>(new ErrorDetailsDto(LocalDateTime.now(), message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFound(ResourceNotFoundException ex) {

        return new ResponseEntity<>(new ErrorDetailsDto(LocalDateTime.now(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MongoWriteException.class})
    public ResponseEntity<ErrorDetailsDto> handleMongoWriteException() {

        return new ResponseEntity<>(new ErrorDetailsDto(LocalDateTime.now(), "MongoDB writing error"), HttpStatus.BAD_REQUEST);
    }
}
