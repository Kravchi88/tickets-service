package com.kravchi88.tickets.purchase.model;

import com.kravchi88.tickets.ticket.model.Ticket;

import java.time.OffsetDateTime;

public record PurchasedTicket(
        long purchaseId,
        Ticket ticket,
        long ownerId,
        long purchasePrice,
        OffsetDateTime purchaseTs
) {}