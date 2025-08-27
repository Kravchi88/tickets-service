package com.kravchi88.tickets.admin.carrier.repository;

import com.kravchi88.tickets.admin.carrier.model.AdminCarrier;
import com.kravchi88.tickets.common.error.exception.DuplicateValueException;
import com.kravchi88.tickets.common.error.exception.NotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Objects;

@Repository
public class JdbcAdminCarrierRepository implements AdminCarrierRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public JdbcAdminCarrierRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String SQL_INSERT = """
        INSERT INTO carrier (carrier_name, phone)
        VALUES (:name, :phone)
        RETURNING id
        """;

    private static final String SQL_UPDATE = """
        UPDATE carrier
           SET carrier_name = :name,
               phone = :phone
         WHERE id = :id
        """;

    private static final String SQL_DELETE = """
        DELETE FROM carrier WHERE id = :id
        """;

    @Override
    public long insert(AdminCarrier carrier) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("name", carrier.name(), Types.VARCHAR)
                .addValue("phone", carrier.phone(), Types.VARCHAR);
        try {
            Long id = jdbc.queryForObject(SQL_INSERT, parameterSource, Long.class);
            return Objects.requireNonNull(id, "INSERT returned no id");
        } catch (DuplicateKeyException e) {
            throw new DuplicateValueException("carrier_name", carrier.name());
        }
    }

    @Override
    public void update(long id, AdminCarrier carrier) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id, Types.BIGINT)
                .addValue("name", carrier.name(), Types.VARCHAR)
                .addValue("phone", carrier.phone(), Types.VARCHAR);
        try {
            int updated = jdbc.update(SQL_UPDATE, parameterSource);
            if (updated == 0) throw new NotFoundException("Carrier", id);
        } catch (DuplicateKeyException e) {
            throw new DuplicateValueException("carrier_name", carrier.name());
        }
    }

    @Override
    public void delete(long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id, Types.BIGINT);
        int deleted = jdbc.update(SQL_DELETE, parameterSource);
        if (deleted == 0) throw new NotFoundException("Carrier", id);
    }
}