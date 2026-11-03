package com.scalux.stockManagement.security.exceptions;

public class OldPasswordIncorrectException extends RuntimeException {
    public OldPasswordIncorrectException(String message) {
        super(message);
    }
}
