package com.kravchi88.tickets.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties(
        String issuer,
        String secret,
        int accessTtlMinutes,
        int refreshTtlDays
) {}