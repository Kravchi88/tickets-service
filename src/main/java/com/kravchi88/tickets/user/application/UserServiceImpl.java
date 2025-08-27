package com.kravchi88.tickets.user.application;

import com.kravchi88.tickets.user.application.dto.UserRegisterData;
import com.kravchi88.tickets.user.model.User;
import com.kravchi88.tickets.user.model.UserRole;
import com.kravchi88.tickets.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserRegisterData data) {
        String login = data.login();
        String passwordHash = passwordEncoder.encode(data.password());
        String fullName = data.fullName();
        User userToSave = new User(null, login, passwordHash, fullName, UserRole.ROLE_BUYER);

        long id = repository.insert(userToSave);
        return new User(id, userToSave.login(), userToSave.passwordHash(), userToSave.fullName(), UserRole.ROLE_BUYER);
    }
}