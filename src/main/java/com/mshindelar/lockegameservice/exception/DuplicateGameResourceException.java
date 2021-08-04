package com.mshindelar.lockegameservice.exception;

public class DuplicateGameResourceException extends RuntimeException {
    public DuplicateGameResourceException() { super(); }

    public DuplicateGameResourceException(String message) { super(message); }
}
