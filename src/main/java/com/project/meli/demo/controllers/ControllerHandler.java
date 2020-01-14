package com.project.meli.demo.controllers;

import com.project.meli.demo.entities.ExceptionResponse;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.exceptions.NotSubStatusException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * ErrorHandling controller.
 */
@RestControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {
    private ExceptionResponse exceptionResponse;
    private static final String MESSAGE_ERROR_VALIDATE = "Validate error in request";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionResponse> handleAllExceptions(final Exception exception, final WebRequest request) {
        exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({NotStatusException.class, NotSubStatusException.class})
    public ResponseEntity<ExceptionResponse> handleNotFoundExceptions(final Exception exception, final WebRequest request) {
        exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ExceptionResponse> handleBadRequestExceptions(final Exception exception, final WebRequest request) {
        exceptionResponse = new ExceptionResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers, final HttpStatus status,
                                                                  final WebRequest request) {
        exceptionResponse = new ExceptionResponse(LocalDateTime.now(), MESSAGE_ERROR_VALIDATE, request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}