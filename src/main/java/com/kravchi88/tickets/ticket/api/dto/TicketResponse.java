package com.kravchi88.tickets.ticket.api.dto;

import java.time.OffsetDateTime;

public record TicketResponse(
        long id,
        String origin,
        String destination,
        String carrier,
        OffsetDateTime departureTs,
        int durationMinutes,
        String seatNumber,
        long price
) {}