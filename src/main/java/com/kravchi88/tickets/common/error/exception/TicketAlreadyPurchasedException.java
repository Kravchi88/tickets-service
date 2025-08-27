package com.kravchi88.tickets.common.error.exception;

public class TicketAlreadyPurchasedException extends RuntimeException {
    public TicketAlreadyPurchasedException(long ticketId) {
        super("Ticket " + ticketId + " is already purchased");
    }
}
