package com.kravchi88.tickets.user.repository;

import com.kravchi88.tickets.user.model.User;

import java.util.Optional;

public interface UserRepository {
    long insert(User user);
    Optional<User> findById(long id);
    Optional<User> findByLogin(String login);
}