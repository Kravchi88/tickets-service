package com.kravchi88.tickets.common.error;

public class LoginAlreadyExistsException extends RuntimeException {
    public LoginAlreadyExistsException(String login) {
        super("Login " + login + " already exists");
    }
}
