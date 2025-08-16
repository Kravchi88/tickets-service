package com.kravchi88.tickets.user.api;

import com.kravchi88.tickets.user.api.mapper.UserWebMapper;
import com.kravchi88.tickets.user.api.dto.UserRegisterRequest;
import com.kravchi88.tickets.user.api.dto.UserResponse;
import com.kravchi88.tickets.user.application.dto.UserRegisterData;
import com.kravchi88.tickets.user.application.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    private final UserWebMapper mapper;

    public UserController(UserService service, UserWebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        UserRegisterData data = mapper.toRegisterData(request);
        UserResponse created = mapper.toResponse(service.register(data));
        URI location = URI.create("/api/users/" + created.id());
        return ResponseEntity.created(location).body(created);
    }
}