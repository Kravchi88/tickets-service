package com.kravchi88.tickets.user.application;

import com.kravchi88.tickets.user.application.dto.UserRegisterData;
import com.kravchi88.tickets.user.model.User;

public interface UserService {
    User register(UserRegisterData data);
}
