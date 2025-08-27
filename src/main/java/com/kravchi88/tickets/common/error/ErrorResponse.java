package com.kravchi88.tickets.common.error;

import java.util.List;
import java.util.Map;

public record ErrorResponse(
        String error,
        String message,
        Map<String, List<String>> issues
) {}