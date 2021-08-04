package com.mshindelar.lockegameservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class RestExceptionHandler{

    @ExceptionHandler(GameResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleGameResourceNotFoundException(GameResourceNotFoundException ex) {
        return buildApiErrorResponse(ex.getMessage(), ex.toString(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return buildApiErrorResponse(ex.getMessage(), ex.toString(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception ex) {
        return buildApiErrorResponse(ex.getMessage(), ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ApiErrorResponse> buildApiErrorResponse(String message, String exception, HttpStatus httpStatus) {
        ApiErrorResponse apiResponse = new ApiErrorResponse
                .ApiErrorResponseBuilder()
                .withException(exception)
                .withMessage(message)
                .withStatus("" + httpStatus.value())
                .withError(httpStatus)
                .atTime(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        return new ResponseEntity<>(apiResponse, httpStatus);
    }
}
