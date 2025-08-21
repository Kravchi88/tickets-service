package com.kravchi88.tickets.purchase.repository;

import com.kravchi88.tickets.purchase.model.PurchasedTicket;

public interface PurchaseRepository {
    long insertPurchase(long userId, long ticketId);
    PurchasedTicket loadPurchasedTicket(long purchaseId);
}
