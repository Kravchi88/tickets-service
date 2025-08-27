package com.kravchi88.tickets.security.api;

import com.kravchi88.tickets.security.api.dto.LoginRequest;
import com.kravchi88.tickets.security.api.dto.RefreshRequest;
import com.kravchi88.tickets.security.api.dto.TokenPairResponse;
import com.kravchi88.tickets.security.api.mapper.AuthorizationWebMapper;
import com.kravchi88.tickets.security.application.AuthorizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authorization")
@Tag(name = "Authorization", description = "Авторизация и обновление токенов")
public class AuthorizationController {
    private final AuthorizationService service;
    private final AuthorizationWebMapper mapper;

    public AuthorizationController(AuthorizationService service, AuthorizationWebMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    @Operation(summary = "Войти", description = "Аутентификация пользователя и выдача пары токенов (access/refresh)")
    public ResponseEntity<TokenPairResponse> login(@RequestBody LoginRequest request) {
        var tokenPair = service.login(mapper.toCredentials(request));
        return ResponseEntity.ok(mapper.toResponse(tokenPair));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Обновить access-токен", description = "Обновление access-токена по refresh-токену")
    public ResponseEntity<TokenPairResponse> refresh(@RequestBody RefreshRequest request) {
        var tokenPair = service.refresh(request.refreshToken());
        return ResponseEntity.ok(mapper.toResponse(tokenPair));
    }
}