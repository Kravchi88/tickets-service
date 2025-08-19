package com.kravchi88.tickets.user.application;

import com.kravchi88.tickets.user.application.dto.UserRegisterData;
import com.kravchi88.tickets.user.model.User;
import com.kravchi88.tickets.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public User register(UserRegisterData data) {
        String login = data.login();
        String passwordHash = encoder.encode(data.password());
        String fullName = data.fullName();
        User userToSave = new User(null, login, passwordHash, fullName);

        long id = repository.insert(userToSave);
        return new User(id, userToSave.login(), userToSave.passwordHash(), userToSave.fullName());
    }
}