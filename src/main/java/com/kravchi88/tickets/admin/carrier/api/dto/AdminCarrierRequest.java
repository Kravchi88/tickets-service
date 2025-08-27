package com.kravchi88.tickets.admin.carrier.api.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminCarrierRequest(
        @NotBlank(message = "name is required")
        String name,
        @NotBlank(message = "phone is required")
        String phone
) {}