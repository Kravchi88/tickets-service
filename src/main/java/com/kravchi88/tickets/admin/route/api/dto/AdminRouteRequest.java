package com.kravchi88.tickets.admin.route.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AdminRouteRequest(
        @NotBlank(message = "origin is required")
        String origin,
        @NotBlank(message = "destination is required")
        String destination,
        @NotNull(message = "carrierId is required")
        Long carrierId,
        @Positive(message = "durationMinutes must be > 0")
        Integer durationMinutes
) {}