package com.project.meli.demo.controllers;

import com.project.meli.demo.entities.ExceptionResponse;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.exceptions.NotSubStatusException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ErrorHandling controller.
 */
@RestControllerAdvice
public class ControllerHandler extends ResponseEntityExceptionHandler {
    private ExceptionResponse exceptionResponse;
    private static final String CHAR_SEPARATE_MESSAGES = ", ";

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
        //Get all errors
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        exceptionResponse = new ExceptionResponse(LocalDateTime.now(),
                String.join(CHAR_SEPARATE_MESSAGES, errors), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}