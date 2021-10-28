package com.mshindelar.lockegameservice.exception;

public class DuplicateEncounterException extends RuntimeException {
    public DuplicateEncounterException() { super(); }

    public DuplicateEncounterException(String message) { super(message); }
}
