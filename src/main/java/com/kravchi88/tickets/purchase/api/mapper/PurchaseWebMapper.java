package com.kravchi88.tickets.purchase.api.mapper;

import com.kravchi88.tickets.purchase.api.dto.PurchasedTicketResponse;
import com.kravchi88.tickets.ticket.api.dto.TicketResponse;
import com.kravchi88.tickets.purchase.application.dto.PurchaseTicketCommand;
import com.kravchi88.tickets.purchase.model.PurchasedTicket;
import com.kravchi88.tickets.ticket.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class PurchaseWebMapper {

    public PurchaseTicketCommand toPurchaseTicketCommand(long userId, long ticketId) {
        return new PurchaseTicketCommand(userId, ticketId);
    }

    public PurchasedTicketResponse toPurchasedResponse(PurchasedTicket purchasedTicket) {
        return new PurchasedTicketResponse(
                purchasedTicket.purchaseId(),
                toResponseWithoutPrice(purchasedTicket.ticket()),
                purchasedTicket.purchasePrice(),
                purchasedTicket.purchaseTs());
    }

    private TicketResponse toResponseWithoutPrice(Ticket ticket) {
        return new TicketResponse(
                ticket.id(),
                ticket.origin(),
                ticket.destination(),
                ticket.carrier(),
                ticket.departureTs(),
                ticket.durationMinutes(),
                ticket.seatNumber(),
                null
        );
    }
}
