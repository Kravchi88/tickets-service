package com.kravchi88.tickets.purchase.repository;

import com.kravchi88.tickets.common.error.exception.TicketAlreadyPurchasedException;
import com.kravchi88.tickets.common.error.exception.TicketNotFoundException;
import com.kravchi88.tickets.common.error.exception.UserNotFoundException;
import com.kravchi88.tickets.purchase.model.PurchasedTicket;
import com.kravchi88.tickets.purchase.repository.mapper.PurchasedTicketRowMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Objects;

@Repository
public class JdbcPurchaseRepository implements PurchaseRepository {
    private final NamedParameterJdbcTemplate jdbc;
    private final PurchasedTicketRowMapper purchasedTicketRowMapper;

    public JdbcPurchaseRepository(NamedParameterJdbcTemplate jdbc, PurchasedTicketRowMapper purchasedTicketRowMapper) {
        this.jdbc = jdbc;
        this.purchasedTicketRowMapper = purchasedTicketRowMapper;
    }

    private static final String SQL_INSERT_PURCHASE = """
            INSERT INTO ticket_purchase (user_id, ticket_id, price, purchase_ts)
            SELECT :userId, t.id, t.price, now()
            FROM ticket t
            WHERE t.id = :ticketId
            RETURNING id
            """;

    private static final String SQL_SELECT_PURCHASED = """
            SELECT
             tp.id AS purchase_id,
             tp.user_id AS purchase_user_id,
             tp.price AS purchase_price,
             tp.purchase_ts AS purchase_ts,
             t.id,
             r.origin,
             r.destination,
             c.carrier_name,
             t.departure_ts,
             r.duration_minutes,
             t.seat_number,
             t.price
            FROM ticket_purchase tp
            JOIN ticket t ON tp.ticket_id = t.id
            JOIN route r ON t.route_id = r.id
            JOIN carrier c ON r.carrier_id = c.id
            WHERE tp.id = :purchaseId
            """;

    @Override
    public long insertPurchase(long userId, long ticketId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("userId", userId, Types.BIGINT)
                .addValue("ticketId", ticketId, Types.BIGINT);

        try {
            Long id = jdbc.queryForObject(SQL_INSERT_PURCHASE, parameterSource, Long.class);
            return Objects.requireNonNull(id, "INSERT returned no id");
        } catch (EmptyResultDataAccessException e) {
            throw new TicketNotFoundException(ticketId);
        } catch (DuplicateKeyException e) {
            throw new TicketAlreadyPurchasedException(ticketId);
        } catch (DataIntegrityViolationException e) {
            throw new UserNotFoundException(userId);
        }
    }

    @Override
    public PurchasedTicket loadPurchasedTicket(long purchaseId) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("purchaseId", purchaseId, Types.BIGINT);

        return jdbc.queryForObject(SQL_SELECT_PURCHASED, parameterSource, purchasedTicketRowMapper);
    }
}
