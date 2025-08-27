package com.kravchi88.tickets.ticket.repository;

import com.kravchi88.tickets.common.page.Slice;
import com.kravchi88.tickets.ticket.application.dto.TicketSearchParams;
import com.kravchi88.tickets.ticket.model.Ticket;
import com.kravchi88.tickets.ticket.repository.mapper.TicketRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class JdbcTicketRepository implements TicketRepository {
    private final NamedParameterJdbcTemplate jdbc;
    private final TicketRowMapper ticketRowMapper;

    public JdbcTicketRepository(NamedParameterJdbcTemplate jdbc, TicketRowMapper ticketRowMapper) {
        this.jdbc = jdbc;
        this.ticketRowMapper = ticketRowMapper;
    }

    private static final String SQL_FIND_AVAILABLE = """
            SELECT
             t.id,
             r.origin,
             r.destination,
             c.carrier_name,
             t.departure_ts,
             r.duration_minutes,
             t.seat_number,
             t.price
            FROM ticket t
            JOIN route r ON t.route_id = r.id
            JOIN carrier c ON r.carrier_id = c.id
            WHERE t.is_sold = false
              AND (:origin IS NULL OR r.origin ILIKE ('%' || :origin || '%'))
              AND (:destination IS NULL OR r.destination ILIKE ('%' || :destination || '%'))
              AND (:carrier IS NULL OR c.carrier_name ILIKE ('%' || :carrier || '%'))
              AND t.departure_ts >= COALESCE(:fromTime, '-infinity'::timestamptz)
              AND t.departure_ts <= COALESCE(:toTime, 'infinity'::timestamptz)
            ORDER BY t.departure_ts, t.id
            LIMIT :limit OFFSET :offset
            """;

    private static final String SQL_MARK_SOLD = """
            UPDATE ticket SET is_sold = TRUE
            WHERE id = :ticketId AND is_sold = FALSE
            """;

    @Override
    public Slice<Ticket> findAvailable(TicketSearchParams params) {
        int limitPlusOne = params.size() + 1;
        int offset = params.page() * params.size();

        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("origin", params.originLike(), Types.VARCHAR)
                .addValue("destination", params.destinationLike(), Types.VARCHAR)
                .addValue("carrier", params.carrierLike(), Types.VARCHAR)
                .addValue("fromTime", params.fromTime(), Types.TIMESTAMP_WITH_TIMEZONE)
                .addValue("toTime", params.toTime(), Types.TIMESTAMP_WITH_TIMEZONE)
                .addValue("limit", limitPlusOne, Types.INTEGER)
                .addValue("offset", offset, Types.INTEGER);

        List<Ticket> rows = jdbc.query(SQL_FIND_AVAILABLE, parameterSource, ticketRowMapper);

        boolean hasNext = rows.size() > params.size();
        List<Ticket> items = hasNext ? rows.subList(0, params.size()) : rows;

        return new Slice<>(items, hasNext);
    }

    @Override
    public void markTicketSold(long ticketId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("ticketId", ticketId, Types.BIGINT);
        jdbc.update(SQL_MARK_SOLD, parameterSource);
    }
}