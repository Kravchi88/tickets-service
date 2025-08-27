package com.kravchi88.tickets.common.page;

import java.util.List;

public record PageResponse<T>(
        List<T> items,
        int page,
        int size,
        boolean hasNext
) {}