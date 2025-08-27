package com.kravchi88.tickets.admin.ticket.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

public record AdminTicketRequest(
        @NotNull(message = "routeId is required")
        Long routeId,
        @NotNull(message = "departureTs is required")
        OffsetDateTime departureTs,
        @NotBlank(message = "seatNumber is required")
        String seatNumber,
        @Positive(message = "price must be >= 1")
        Long price,
        @NotNull(message = "isSold is required")
        Boolean isSold
) {}