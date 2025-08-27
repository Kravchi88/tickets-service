package com.kravchi88.tickets.common.page;

import java.util.List;

public record PageData<T>(
        List<T> items,
        int page,
        int size,
        boolean hasNext
) {}