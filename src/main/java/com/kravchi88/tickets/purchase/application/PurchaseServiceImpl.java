package com.kravchi88.tickets.purchase.application;

import com.kravchi88.tickets.purchase.application.dto.PurchaseTicketCommand;
import com.kravchi88.tickets.purchase.model.PurchasedTicket;
import com.kravchi88.tickets.purchase.repository.PurchaseRepository;
import com.kravchi88.tickets.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final TicketRepository ticketRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, TicketRepository ticketRepository) {
        this.purchaseRepository = purchaseRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    @Override
    public PurchasedTicket purchaseTicket(PurchaseTicketCommand data) {
        long purchaseId = purchaseRepository.insertPurchase(data.userId(), data.ticketId());
        ticketRepository.markTicketSold(data.ticketId());
        return purchaseRepository.loadPurchasedTicket(purchaseId);
    }
}
