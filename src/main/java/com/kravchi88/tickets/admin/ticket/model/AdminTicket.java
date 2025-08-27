package com.kravchi88.tickets.admin.ticket.model;

import java.time.OffsetDateTime;

public record AdminTicket(
        long routeId,
        OffsetDateTime departureTs,
        String seatNumber,
        long price,
        boolean isSold
) {}