package com.scalux.stockManagement.security.exceptions;

public class UserAllReadyExistException extends RuntimeException {
    public UserAllReadyExistException(String message) {
        super(message);
    }
}
