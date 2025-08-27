package com.kravchi88.tickets.admin.route.repository;

import com.kravchi88.tickets.admin.route.model.AdminRoute;
import com.kravchi88.tickets.common.error.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Objects;

@Repository
public class JdbcAdminRouteRepository implements AdminRouteRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public JdbcAdminRouteRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String SQL_INSERT = """
        INSERT INTO route (origin, destination, carrier_id, duration_minutes)
        VALUES (:origin, :destination, :carrierId, :duration)
        RETURNING id
        """;

    private static final String SQL_UPDATE = """
        UPDATE route
           SET origin = :origin,
               destination = :destination,
               carrier_id = :carrierId,
               duration_minutes = :duration
         WHERE id = :id
        """;

    private static final String SQL_DELETE = """
        DELETE FROM route WHERE id = :id
        """;

    @Override
    public long insert(AdminRoute route) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("origin", route.origin(), Types.VARCHAR)
                .addValue("destination", route.destination(), Types.VARCHAR)
                .addValue("carrierId", route.carrierId(), Types.BIGINT)
                .addValue("duration", route.durationMinutes(), Types.INTEGER);
        try {
            Long id = jdbc.queryForObject(SQL_INSERT, parameterSource, Long.class);
            return Objects.requireNonNull(id, "INSERT returned no id");
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Carrier", route.carrierId());
        }
    }

    @Override
    public void update(long id, AdminRoute route) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id, Types.BIGINT)
                .addValue("origin", route.origin(), Types.VARCHAR)
                .addValue("destination", route.destination(), Types.VARCHAR)
                .addValue("carrierId", route.carrierId(), Types.BIGINT)
                .addValue("duration", route.durationMinutes(), Types.INTEGER);
        try {
            int updated = jdbc.update(SQL_UPDATE, parameterSource);
            if (updated == 0) throw new NotFoundException("Route", id);
        } catch (DataIntegrityViolationException e) {
            throw new NotFoundException("Carrier", route.carrierId());
        }
    }

    @Override
    public void delete(long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id, Types.BIGINT);
        int deleted = jdbc.update(SQL_DELETE, parameterSource);
        if (deleted == 0) throw new NotFoundException("Route", id);
    }
}