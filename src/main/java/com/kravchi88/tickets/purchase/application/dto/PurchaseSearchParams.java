package com.kravchi88.tickets.purchase.application.dto;

public record PurchaseSearchParams(
        long userId,
        int page,
        int size
) {}
