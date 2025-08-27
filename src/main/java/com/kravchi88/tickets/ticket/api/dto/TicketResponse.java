package com.kravchi88.tickets.ticket.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TicketResponse(
        long id,
        String origin,
        String destination,
        String carrier,
        OffsetDateTime departureTs,
        int durationMinutes,
        String seatNumber,
        Long price
) {}