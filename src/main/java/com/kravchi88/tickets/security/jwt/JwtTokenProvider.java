package com.kravchi88.tickets.security.jwt;

import com.kravchi88.tickets.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {
    public static final String CLAIM_LOGIN = "login";
    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_TYPE = "typ";
    public static final String TYPE_ACCESS = "access";
    public static final String TYPE_REFRESH = "refresh";

    private final SecretKey key;
    private final String issuer;
    private final long accessTtlMinutes;
    private final long refreshTtlDays;

    public JwtTokenProvider(JwtProperties props) {
        this.key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
        this.issuer = props.issuer();
        this.accessTtlMinutes = props.accessTtlMinutes();
        this.refreshTtlDays = props.refreshTtlDays();
    }

    public String generateAccessToken(UserPrincipal user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofMinutes(accessTtlMinutes));

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(String.valueOf(user.userId()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .claim(CLAIM_LOGIN, user.login())
                .claim(CLAIM_ROLES, user.roles())
                .claim(CLAIM_TYPE, TYPE_ACCESS)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserPrincipal user) {
        Instant now = Instant.now();
        Instant expiry = now.plus(Duration.ofDays(refreshTtlDays));

        return Jwts.builder()
                .setIssuer(issuer)
                .setSubject(String.valueOf(user.userId()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .claim(CLAIM_TYPE, TYPE_REFRESH)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .requireIssuer(issuer)
                .setSigningKey(key)
                .setAllowedClockSkewSeconds(30)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public UserPrincipal toPrincipal(Claims claims) {
        long userId = Long.parseLong(claims.getSubject());
        String login = claims.get(CLAIM_LOGIN, String.class);
        @SuppressWarnings("unchecked")
        List<String> roles = claims.get(CLAIM_ROLES, List.class);
        if (roles == null) roles = List.of();

        return new UserPrincipal(userId, login, roles);
    }

    public boolean isAccessToken(Claims claims) {
        return TYPE_ACCESS.equals(claims.get(CLAIM_TYPE, String.class));
    }

    public boolean isRefreshToken(Claims claims) {
        return TYPE_REFRESH.equals(claims.get(CLAIM_TYPE, String.class));
    }
}