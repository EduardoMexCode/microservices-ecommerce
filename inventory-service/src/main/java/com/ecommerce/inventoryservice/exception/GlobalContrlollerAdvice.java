package com.ecommerce.inventoryservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalContrlollerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException e, WebRequest request) {

        log.warn("Resource not found - Path: {}, Message: {}", request.getDescription(false), e.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());

        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create("https://api.ecommerce.com/errors/not-found"));
        problemDetail.setProperty("Timestamp", Instant.now().toString());

        problemDetail.setProperty("Resource", e.getResourceName());
        problemDetail.setProperty("Field", e.getFieldName());
        problemDetail.setProperty("Value", e.getFieldValue());

        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation field or fields failed");

        log.error("Validation field or fields failed: {}", e.getMessage());

        problemDetail.setTitle("Validation Failed");
        problemDetail.setType(URI.create("https://api.ecommerce.com/errors/error-validation"));
        problemDetail.setProperty("Timestamp", Instant.now().toString());

        Map<String, String> errorsMap = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errorsMap.put(error.getField(), error.getDefaultMessage());
        });

        problemDetail.setProperty("fields", errorsMap);

        return problemDetail;
    }

    public ProblemDetail handleException(Exception e, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error has occurred. Please contact the administrator.");

        log.error("An unexpected error has occurred. Message: {}", e.getMessage());

        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("https://api.ecommerce.com/errors/internal"));
        problemDetail.setProperty("Timestamp", Instant.now().toString());

        return problemDetail;
    }

}
