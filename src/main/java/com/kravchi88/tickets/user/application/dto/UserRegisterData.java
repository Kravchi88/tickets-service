package com.kravchi88.tickets.user.application.dto;

public record UserRegisterData(
        String login,
        String password,
        String fullName
) {}
