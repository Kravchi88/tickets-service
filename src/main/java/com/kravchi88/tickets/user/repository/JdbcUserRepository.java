package com.kravchi88.tickets.user.repository;

import com.kravchi88.tickets.common.error.LoginAlreadyExistsException;
import com.kravchi88.tickets.user.model.User;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final JdbcTemplate jdbc;

    public JdbcUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final String SQL_INSERT = """
        INSERT INTO app_user (login, password_hash, full_name)
        VALUES (?, ?, ?)
        RETURNING id
        """;

    @Override
    public long insert(User user) {
        try {
            Long id = jdbc.queryForObject(
                    SQL_INSERT, Long.class,
                    user.login(), user.passwordHash(), user.fullName()
            );
            return Objects.requireNonNull(id, "INSERT returned no id");
        } catch (DuplicateKeyException e) {
            throw new LoginAlreadyExistsException(user.login());
        }
    }
}