package com.kravchi88.tickets.purchase.api.dto;

import com.kravchi88.tickets.ticket.api.dto.TicketResponse;

import java.time.OffsetDateTime;

public record PurchasedTicketResponse(
        long purchaseId,
        TicketResponse ticket,
        long purchasePrice,
        OffsetDateTime purchaseTs
) {}