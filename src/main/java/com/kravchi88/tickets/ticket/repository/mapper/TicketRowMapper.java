package com.kravchi88.tickets.ticket.repository.mapper;

import com.kravchi88.tickets.ticket.model.Ticket;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;

@Component
public class TicketRowMapper implements RowMapper<Ticket> {

    @Override
    public Ticket mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Ticket(
                rs.getLong("id"),
                rs.getString("origin"),
                rs.getString("destination"),
                rs.getString("carrier_name"),
                rs.getObject("departure_ts", OffsetDateTime.class),
                rs.getInt("duration_minutes"),
                rs.getString("seat_number"),
                rs.getLong("price")
        );
    }
}