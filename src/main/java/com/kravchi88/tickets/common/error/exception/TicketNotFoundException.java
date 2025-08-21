package com.kravchi88.tickets.common.error.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(long ticketId) {
        super("Ticket " + ticketId + " not found");
    }
}