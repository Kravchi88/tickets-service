package com.kravchi88.tickets.common.error.exception;

public class DuplicateValueException extends RuntimeException {
    public DuplicateValueException(String column, String value) {
        super("Value " + value + " already exists in column " + column);
    }
}
