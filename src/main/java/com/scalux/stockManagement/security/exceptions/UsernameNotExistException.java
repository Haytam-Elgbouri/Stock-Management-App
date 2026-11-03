package com.scalux.stockManagement.security.exceptions;

public class UsernameNotExistException extends RuntimeException {
    public UsernameNotExistException(String message) {
        super(message);
    }
}
