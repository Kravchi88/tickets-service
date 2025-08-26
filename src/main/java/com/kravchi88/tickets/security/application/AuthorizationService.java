package com.kravchi88.tickets.security.application;

import com.kravchi88.tickets.security.application.dto.Credentials;
import com.kravchi88.tickets.security.application.dto.TokenPair;

public interface AuthorizationService {
    TokenPair login(Credentials credentials);
    TokenPair refresh(String refreshToken);
}