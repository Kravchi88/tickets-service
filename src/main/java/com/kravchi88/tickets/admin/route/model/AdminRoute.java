package com.kravchi88.tickets.admin.route.model;

public record AdminRoute(
        String origin,
        String destination,
        long carrierId,
        int durationMinutes
) {}