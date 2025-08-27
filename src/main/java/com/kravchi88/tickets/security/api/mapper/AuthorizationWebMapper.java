package com.kravchi88.tickets.security.api.mapper;

import com.kravchi88.tickets.security.api.dto.LoginRequest;
import com.kravchi88.tickets.security.api.dto.TokenPairResponse;
import com.kravchi88.tickets.security.application.dto.Credentials;
import com.kravchi88.tickets.security.application.dto.TokenPair;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationWebMapper {
    public Credentials toCredentials(LoginRequest request) {
        return new Credentials(request.login(), request.password());
    }

    public TokenPairResponse toResponse(TokenPair pair) {
        return new TokenPairResponse(pair.accessToken(), pair.refreshToken(), "Bearer");
    }
}
