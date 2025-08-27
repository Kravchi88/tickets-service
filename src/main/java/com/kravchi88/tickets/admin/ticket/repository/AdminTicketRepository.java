package com.kravchi88.tickets.admin.ticket.repository;

import com.kravchi88.tickets.admin.ticket.model.AdminTicket;

public interface AdminTicketRepository {
    long insert(AdminTicket ticket);
    void update(long id, AdminTicket ticket);
    void delete(long id);
}