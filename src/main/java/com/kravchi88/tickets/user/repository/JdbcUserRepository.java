package com.kravchi88.tickets.user.repository;

import com.kravchi88.tickets.common.error.exception.LoginAlreadyExistsException;
import com.kravchi88.tickets.user.model.User;
import com.kravchi88.tickets.user.repository.mapper.UserRowMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final NamedParameterJdbcTemplate jdbc;
    private final UserRowMapper userRowMapper;

    public JdbcUserRepository(NamedParameterJdbcTemplate jdbc, UserRowMapper userRowMapper) {
        this.jdbc = jdbc;
        this.userRowMapper = userRowMapper;
    }

    private static final String SQL_INSERT = """
        INSERT INTO app_user (login, password_hash, full_name)
        VALUES (:login, :passwordHash, :fullName)
        RETURNING id
        """;

    private static final String SQL_FIND_BY_ID = """
        SELECT id, login, password_hash, full_name
        FROM app_user
        WHERE id = :id
        """;

    private static final String SQL_FIND_BY_LOGIN = """
        SELECT id, login, password_hash, full_name
        FROM app_user
        WHERE login = :login
        """;

    @Override
    public long insert(User user) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("login", user.login(), Types.VARCHAR)
                .addValue("passwordHash", user.passwordHash(), Types.VARCHAR)
                .addValue("fullName", user.fullName(), Types.VARCHAR);

        try {
            Long id = jdbc.queryForObject(SQL_INSERT, parameterSource, Long.class);
            return Objects.requireNonNull(id, "INSERT returned no id");
        } catch (DuplicateKeyException e) {
            throw new LoginAlreadyExistsException(user.login());
        }
    }

    @Override
    public Optional<User> findById(long id) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("id", id, Types.BIGINT);

        try {
            User user = jdbc.queryForObject(SQL_FIND_BY_ID, parameterSource, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("login", login, Types.VARCHAR);

        try {
            User user = jdbc.queryForObject(SQL_FIND_BY_LOGIN, parameterSource, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}