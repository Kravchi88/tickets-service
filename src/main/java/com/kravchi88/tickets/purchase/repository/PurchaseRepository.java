package com.kravchi88.tickets.purchase.repository;

import com.kravchi88.tickets.common.page.Slice;
import com.kravchi88.tickets.purchase.application.dto.PurchaseSearchParams;
import com.kravchi88.tickets.purchase.model.PurchasedTicket;

public interface PurchaseRepository {
    long insertPurchase(long userId, long ticketId);
    PurchasedTicket loadPurchasedTicket(long purchaseId);
    Slice<PurchasedTicket> findPurchases(PurchaseSearchParams params);
}
