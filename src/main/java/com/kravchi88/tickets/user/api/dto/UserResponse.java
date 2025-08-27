package com.kravchi88.tickets.user.api.dto;

public record UserResponse(
        long id,
        String login,
        String fullName
) {}