package com.kravchi88.tickets.purchase.api.mapper;

import com.kravchi88.tickets.common.page.PageData;
import com.kravchi88.tickets.common.page.PageResponse;
import com.kravchi88.tickets.purchase.api.dto.PurchasedTicketResponse;
import com.kravchi88.tickets.purchase.application.dto.PurchaseSearchParams;
import com.kravchi88.tickets.ticket.api.dto.TicketResponse;
import com.kravchi88.tickets.purchase.application.dto.PurchaseTicketCommand;
import com.kravchi88.tickets.purchase.model.PurchasedTicket;
import com.kravchi88.tickets.ticket.model.Ticket;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public PageResponse<PurchasedTicketResponse> toResponsePage(PageData<PurchasedTicket> page) {
        List<PurchasedTicketResponse> items = page.items().stream().map(this::toPurchasedResponse).toList();
        return new PageResponse<>(items, page.page(), page.size(), page.hasNext());
    }

    public PurchaseSearchParams toSearchParams(long userId, Integer page, Integer size) {
        return new PurchaseSearchParams(userId, page, size);
    }
}
