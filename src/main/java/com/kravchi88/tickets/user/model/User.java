package com.kravchi88.tickets.user.model;

public record User(
        Long id,
        String login,
        String passwordHash,
        String fullName
) {}