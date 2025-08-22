package com.kravchi88.tickets.purchase.application;

import com.kravchi88.tickets.common.page.PageData;
import com.kravchi88.tickets.purchase.application.dto.PurchaseSearchParams;
import com.kravchi88.tickets.purchase.application.dto.PurchaseTicketCommand;
import com.kravchi88.tickets.purchase.model.PurchasedTicket;

public interface PurchaseService {
    PurchasedTicket purchaseTicket(PurchaseTicketCommand data);
    PageData<PurchasedTicket> getPurchasedTickets(PurchaseSearchParams params);
}
