package com.kravchi88.tickets.ticket.application.dto;

import java.time.OffsetDateTime;

public record TicketSearchParams(
        String originLike,
        String destinationLike,
        String carrierLike,
        OffsetDateTime fromTime,
        OffsetDateTime toTime,
        int page,
        int size
) {}