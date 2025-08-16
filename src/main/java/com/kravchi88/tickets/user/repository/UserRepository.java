package com.kravchi88.tickets.user.repository;

import com.kravchi88.tickets.user.model.User;

public interface UserRepository {
    long insert(User user);
}
