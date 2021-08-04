package com.mshindelar.lockegameservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ImproperGameStateException  extends RuntimeException {
    public ImproperGameStateException() { super(); }

    public ImproperGameStateException(String message) { super(message); }
}