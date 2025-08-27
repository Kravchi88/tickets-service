package com.kravchi88.tickets.purchase.repository.mapper;

import com.kravchi88.tickets.purchase.model.PurchasedTicket;
import com.kravchi88.tickets.ticket.model.Ticket;
import com.kravchi88.tickets.ticket.repository.mapper.TicketRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Component
public class PurchasedTicketRowMapper implements RowMapper<PurchasedTicket> {
    private final TicketRowMapper ticketRowMapper;

    public PurchasedTicketRowMapper(TicketRowMapper ticketRowMapper) {
        this.ticketRowMapper = ticketRowMapper;
    }

    @Override
    public PurchasedTicket mapRow(ResultSet rs, int rowNum) throws SQLException {
        long purchaseId = rs.getLong("purchase_id");
        Ticket ticket = ticketRowMapper.mapRow(rs, rowNum);
        long ownerId = rs.getLong("purchase_user_id");
        long purchasePrice = rs.getLong("purchase_price");
        OffsetDateTime purchaseTs = rs.getObject("purchase_ts", OffsetDateTime.class);

        return new PurchasedTicket(purchaseId, ticket, ownerId, purchasePrice, purchaseTs);
    }
}