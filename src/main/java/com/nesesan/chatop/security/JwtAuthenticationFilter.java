package com.nesesan.chatop.security;

import com.nesesan.chatop.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtAuthenticationFilter is a Spring Security filter that intercepts HTTP requests
 * to perform JWT-based authentication. It parses the Authorization header,
 * validates the provided JWT token, and sets the authenticated user in the
 * SecurityContext.
 *
 * This filter ensures that users with valid JWT tokens are authenticated and allows for
 * secure communication in applications using stateless authentication.
 *
 * Key functionalities:
 * - Extracts the JWT token from the Authorization header of the incoming HTTP request.
 * - Validates the token using the JwtUtil utility to ensure its authenticity and validity.
 * - Loads the user details using the UserService and sets authenticated user information
 *   in the SecurityContext.
 *
 * Dependencies:
 * - JwtUtil: Utility class to manage and validate JWT tokens.
 * - UserService: Service class to retrieve user details from the database.
 *
 * Usage:
 * This filter is typically used in conjunction with Spring Security configurations
 * to enable JWT authentication in stateless applications.
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Lazy
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                username = jwtUtil.extractUsername(token);
            } catch (Exception e) {
                logger.error("Invalid JWT token: " + e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);

            if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}