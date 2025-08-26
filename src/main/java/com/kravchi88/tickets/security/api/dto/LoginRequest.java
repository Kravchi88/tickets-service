package com.kravchi88.tickets.security.api.dto;

public record LoginRequest(
        String login,
        String password
) {}
