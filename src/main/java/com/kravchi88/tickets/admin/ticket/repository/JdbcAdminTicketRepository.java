package com.kravchi88.tickets.admin.ticket.repository;

import com.kravchi88.tickets.admin.ticket.model.AdminTicket;
import com.kravchi88.tickets.common.error.exception.DuplicateValueException;
import com.kravchi88.tickets.common.error.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Objects;

@Repository
public class JdbcAdminTicketRepository implements AdminTicketRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public JdbcAdminTicketRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String SQL_INSERT = """
        INSERT INTO ticket (route_id, departure_ts, seat_number, price, is_sold)
        VALUES (:routeId, :departureTs, :seatNumber, :price, :isSold)
        RETURNING id
        """;

    private static final String SQL_UPDATE = """
        UPDATE ticket
           SET route_id = :routeId,
               departure_ts = :departureTs,
               seat_number = :seatNumber,
               price = :price,
               is_sold = :isSold
         WHERE id = :id
        """;

    private static final String SQL_DELETE = """
        DELETE FROM ticket WHERE id = :id
        """;

    @Override
    public long insert(AdminTicket ticket) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("routeId", ticket.routeId(), Types.BIGINT)
                .addValue("departureTs", ticket.departureTs())
                .addValue("seatNumber", ticket.seatNumber(), Types.VARCHAR)
                .addValue("price", ticket.price(), Types.BIGINT)
                .addValue("isSold", ticket.isSold(), Types.BOOLEAN);
        try {
            Long id = jdbc.queryForObject(SQL_INSERT, parameterSource, Long.class);
            return Objects.requireNonNull(id, "INSERT returned no id");
        } catch (DuplicateKeyException e) {
            throw new DuplicateValueException("route_id/departure_ts/seat_number",
                    ticket.routeId() + "/" + ticket.departureTs() + "/" + ticket.seatNumber());
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Route", ticket.routeId());
        }
    }

    @Override
    public void update(long id, AdminTicket ticket) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id, Types.BIGINT)
                .addValue("routeId", ticket.routeId(), Types.BIGINT)
                .addValue("departureTs", ticket.departureTs())
                .addValue("seatNumber", ticket.seatNumber(), Types.VARCHAR)
                .addValue("price", ticket.price(), Types.BIGINT)
                .addValue("isSold", ticket.isSold(), Types.BOOLEAN);
        try {
            int updated = jdbc.update(SQL_UPDATE, parameterSource);
            if (updated == 0) throw new NotFoundException("Ticket", id);
        } catch (DuplicateKeyException e) {
            throw new DuplicateValueException("route_id/departure_ts/seat_number",
                    ticket.routeId() + "/" + ticket.departureTs() + "/" + ticket.seatNumber());
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Route", ticket.routeId());
        }
    }

    @Override
    public void delete(long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id, Types.BIGINT);
        int deleted = jdbc.update(SQL_DELETE, parameterSource);
        if (deleted == 0) throw new NotFoundException("Ticket", id);
    }
}