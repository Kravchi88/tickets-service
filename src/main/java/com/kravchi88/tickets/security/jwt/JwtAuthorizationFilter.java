package com.kravchi88.tickets.security.jwt;

import com.kravchi88.tickets.security.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenProvider tokenProvider;

    public JwtAuthorizationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String authorization = request.getHeader(AUTHORIZATION);

            if (StringUtils.hasText(authorization) && authorization.startsWith(BEARER_PREFIX)) {
                String token = authorization.substring(BEARER_PREFIX.length()).trim();
                if (!token.isEmpty()) {
                    try {
                        Claims claims = tokenProvider.parseClaims(token);
                        if (tokenProvider.isAccessToken(claims)) {
                            UserPrincipal principal = tokenProvider.toPrincipal(claims);

                            List<SimpleGrantedAuthority> authorities = principal.roles().stream()
                                    .map(SimpleGrantedAuthority::new)
                                    .collect(Collectors.toList());

                            var authentication = new UsernamePasswordAuthenticationToken(
                                    principal,
                                    null,
                                    authorities
                            );

                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }

                    } catch (JwtException | IllegalArgumentException e) {
                        SecurityContextHolder.clearContext();
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}