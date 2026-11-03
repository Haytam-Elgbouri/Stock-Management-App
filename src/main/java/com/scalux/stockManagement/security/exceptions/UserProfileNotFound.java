package com.scalux.stockManagement.security.exceptions;

public class UserProfileNotFound extends RuntimeException {
    public UserProfileNotFound(String message) {
        super(message);
    }
}
