package com.kravchi88.tickets.ticket.api.mapper;

import com.kravchi88.tickets.common.page.PageData;
import com.kravchi88.tickets.common.page.PageResponse;
import com.kravchi88.tickets.ticket.api.dto.TicketResponse;
import com.kravchi88.tickets.ticket.application.dto.TicketSearchParams;
import com.kravchi88.tickets.ticket.model.Ticket;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class TicketWebMapper {

    public TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.id(),
                ticket.origin(),
                ticket.destination(),
                ticket.carrier(),
                ticket.departureTs(),
                ticket.durationMinutes(),
                ticket.seatNumber(),
                ticket.price()
        );
    }

    public PageResponse<TicketResponse> toResponsePage(PageData<Ticket> page) {
        List<TicketResponse> items = page.items().stream().map(this::toResponse).toList();
        return new PageResponse<>(items, page.page(), page.size(), page.hasNext());
    }

    public TicketSearchParams toSearchParams(
            String origin, String destination, String carrier,
            OffsetDateTime fromTime, OffsetDateTime toTime,
            int page, int size) {

        String originLike = (origin == null || origin.isBlank()) ? null : origin.trim();
        String destinationLike = (destination == null || destination.isBlank()) ? null : destination.trim();
        String carrierLike = (carrier == null || carrier.isBlank()) ? null : carrier.trim();

        return new TicketSearchParams(originLike, destinationLike, carrierLike, fromTime, toTime, page, size);
    }
}