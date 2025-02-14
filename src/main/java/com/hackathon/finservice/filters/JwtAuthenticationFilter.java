package com.hackathon.finservice.filters;

import com.hackathon.finservice.Util.JwtUtils;
import com.hackathon.finservice.Service.CustomUserDetailsService;
import com.hackathon.finservice.Repositories.TokenRepository;
import com.hackathon.finservice.Entities.Token;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getRequestURI();

            if (path.startsWith("/api/dashboard/") || path.startsWith("/api/account/") || path.startsWith("/api/users/logout")) {
                String authHeader = request.getHeader(jwtUtils.getJwtHeader());

                if (authHeader == null || authHeader.isEmpty()) {
                    throw new MalformedJwtException("Authorization header is missing or empty.");
                }

                String jwt = jwtUtils.getJwtFromRequest(authHeader);

                if (jwt == null) {
                    throw new MalformedJwtException("JWT token is missing.");
                }

                if (SecurityContextHolder.getContext().getAuthentication() == null) {
                    String username = jwtUtils.extractUsername(jwt);

                    if (jwtUtils.validateToken(jwt)) {
                        Token tokenEntity = tokenRepository.findByToken(jwt).orElse(null);

                        if (tokenEntity != null && tokenEntity.getActive()) {
                            var userDetails = customUserDetailsService.loadUserByUsername(username);

                            if (userDetails != null) {
                                UsernamePasswordAuthenticationToken authToken =
                                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                SecurityContextHolder.getContext().setAuthentication(authToken);
                            } else {
                                throw new IllegalArgumentException("User not found for the provided token.");
                            }
                        } else {
                            throw new IllegalArgumentException("Token is not active.");
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid JWT token.");
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (SignatureException | ExpiredJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Access denied");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Access denied");;
        }
    }
}

