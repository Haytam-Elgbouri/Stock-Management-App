package com.scalux.stockManagement.security.exceptions;

public class UserIsActiveException extends RuntimeException {
    public UserIsActiveException(Object username) {
        super("User with this username <" + username + "> is suspended, you can call the support");
    }
}
