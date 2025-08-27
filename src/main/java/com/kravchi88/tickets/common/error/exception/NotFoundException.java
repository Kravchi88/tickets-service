package com.kravchi88.tickets.common.error.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity, long id) {
        super(entity + " with id " + id + " not found");
    }
}
