package com.kravchi88.tickets.common.page;

import java.util.List;

public record Slice<T>(List<T> items, boolean hasNext) {}