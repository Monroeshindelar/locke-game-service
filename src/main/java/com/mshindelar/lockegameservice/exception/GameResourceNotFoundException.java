package com.mshindelar.lockegameservice.exception;

public class GameResourceNotFoundException extends RuntimeException {
    public GameResourceNotFoundException() { super(); }

    public GameResourceNotFoundException(String message) { super(message); }
}
