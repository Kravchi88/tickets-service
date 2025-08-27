package com.kravchi88.tickets.security.application;

import com.kravchi88.tickets.common.error.exception.InvalidCredentialsException;
import com.kravchi88.tickets.common.error.exception.InvalidRefreshTokenException;
import com.kravchi88.tickets.security.UserPrincipal;
import com.kravchi88.tickets.security.application.dto.Credentials;
import com.kravchi88.tickets.security.application.dto.TokenPair;
import com.kravchi88.tickets.security.jwt.JwtTokenProvider;
import com.kravchi88.tickets.user.model.User;
import com.kravchi88.tickets.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthorizationServiceImpl(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder,
                                    JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public TokenPair login(Credentials credentials) {
        User user = userRepository.findByLogin(credentials.login()).orElseThrow(InvalidCredentialsException::new);

        boolean passwordOk = passwordEncoder.matches(credentials.password(), user.passwordHash());
        if (!passwordOk) throw new InvalidCredentialsException();

        var principal = new UserPrincipal(user.id(), user.login(), List.of(user.role().name()));

        String accessToken  = tokenProvider.generateAccessToken(principal);
        String refreshToken = tokenProvider.generateRefreshToken(principal);

        return new TokenPair(accessToken, refreshToken);
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        try {
            Claims claims = tokenProvider.parseClaims(refreshToken);

            if (!tokenProvider.isRefreshToken(claims)) {
                throw new InvalidRefreshTokenException();
            }

            long userId = Long.parseLong(claims.getSubject());
            User user = userRepository.findById(userId).orElseThrow(InvalidRefreshTokenException::new);

            var principal = new UserPrincipal(user.id(), user.login(), List.of(user.role().name()));
            String newAccessToken = tokenProvider.generateAccessToken(principal);

            return new TokenPair(newAccessToken, refreshToken);

        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidRefreshTokenException();
        }
    }
}