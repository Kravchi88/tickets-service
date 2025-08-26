package com.kravchi88.tickets.security.application.dto;

public record TokenPair(
        String accessToken,
        String refreshToken
) {}
