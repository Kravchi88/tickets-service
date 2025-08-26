package com.kravchi88.tickets.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kravchi88.tickets.common.error.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

public class SecurityErrorWriter {
    private final ObjectMapper mapper;

    public SecurityErrorWriter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void writeUnauthorized(HttpServletResponse response) throws IOException {
        var body = new ErrorResponse(
                "UNAUTHORIZED",
                "Authentication required",
                Map.of("authentication", java.util.List.of("Missing or invalid access token"))
        );
        response.setStatus(401);
        response.setContentType("application/json");
        mapper.writeValue(response.getWriter(), body);
    }
}