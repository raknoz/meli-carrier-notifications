package com.project.meli.demo.entities;

import java.time.LocalDateTime;

/**
 * Entity to wrapper all the exceptions to will be send in case of error.
 */
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ExceptionResponse(final LocalDateTime timestamp, final String message, final String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
