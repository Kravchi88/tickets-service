package com.kravchi88.tickets.ticket.application;

import com.kravchi88.tickets.common.page.PageData;
import com.kravchi88.tickets.common.page.Slice;
import com.kravchi88.tickets.ticket.application.dto.TicketSearchParams;
import com.kravchi88.tickets.ticket.model.Ticket;
import com.kravchi88.tickets.ticket.repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository repository;

    public TicketServiceImpl(TicketRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageData<Ticket> searchAvailableTickets(TicketSearchParams params) {
        Slice<Ticket> slice = repository.findAvailable(params);
        return new PageData<>(slice.items(), params.page(), params.size(), slice.hasNext());
    }
}
