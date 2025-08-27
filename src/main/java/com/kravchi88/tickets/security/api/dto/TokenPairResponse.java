package com.kravchi88.tickets.security.api.dto;

public record TokenPairResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {}
