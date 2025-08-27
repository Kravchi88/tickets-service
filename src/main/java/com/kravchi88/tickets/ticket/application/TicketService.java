package com.kravchi88.tickets.ticket.application;

import com.kravchi88.tickets.common.page.PageData;
import com.kravchi88.tickets.ticket.application.dto.TicketSearchParams;
import com.kravchi88.tickets.ticket.model.Ticket;

public interface TicketService {
    PageData<Ticket> searchAvailableTickets(TicketSearchParams params);
}
