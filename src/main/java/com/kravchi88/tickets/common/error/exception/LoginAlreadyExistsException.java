package com.kravchi88.tickets.common.error.exception;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {
        super("Login " + login + " already exists");
    }
}
