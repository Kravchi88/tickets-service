package com.kravchi88.tickets.admin.ticket.api.mapper;

import com.kravchi88.tickets.admin.ticket.model.AdminTicket;
import com.kravchi88.tickets.admin.ticket.api.dto.AdminTicketRequest;
import org.springframework.stereotype.Component;

@Component
public class AdminTicketWebMapper {
    public AdminTicket toDomain(AdminTicketRequest request) {
        return new AdminTicket(request.routeId(), request.departureTs(), request.seatNumber(), request.price(), request.isSold());
    }
}