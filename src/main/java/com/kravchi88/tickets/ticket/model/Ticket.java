package com.kravchi88.tickets.ticket.model;

import java.time.OffsetDateTime;

public record Ticket(
        long id,
        String origin,
        String destination,
        String carrier,
        OffsetDateTime departureTs,
        int durationMinutes,
        String seatNumber,
        long price
) {}