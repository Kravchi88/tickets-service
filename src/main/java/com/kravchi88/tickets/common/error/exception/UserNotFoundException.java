package com.kravchi88.tickets.common.error.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long userId) {
        super("User " + userId + " not found");
    }
}