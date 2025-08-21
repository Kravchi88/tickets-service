package com.kravchi88.tickets.ticket.repository;

import com.kravchi88.tickets.common.page.Slice;
import com.kravchi88.tickets.ticket.application.dto.TicketSearchParams;
import com.kravchi88.tickets.ticket.model.Ticket;

public interface TicketRepository {
    Slice<Ticket> findAvailable(TicketSearchParams params);
    void markTicketSold(long ticketId);
}
