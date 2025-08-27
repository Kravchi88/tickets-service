package com.kravchi88.tickets.user.api.mapper;

import com.kravchi88.tickets.user.api.dto.UserRegisterRequest;
import com.kravchi88.tickets.user.api.dto.UserResponse;
import com.kravchi88.tickets.user.application.dto.UserRegisterData;
import com.kravchi88.tickets.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserWebMapper {
    public UserRegisterData toRegisterData(UserRegisterRequest request) {
        return new UserRegisterData(
                request.login(),
                request.password(),
                request.fullName()
        );
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.id(), user.login(), user.fullName());
    }
}
