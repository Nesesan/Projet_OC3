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
 * The JwtAuthenticationFilter class is a custom implementation of the
 * Spring Security filter OncePerRequestFilter. It is responsible for
 * processing and validating incoming HTTP request authentication via JWT
 * (JSON Web Token).
 * This filter checks for the presence of an Authorization header in the
 * incoming HTTP request. If the header contains a valid JWT token, the
 * filter validates the token, extracts the username, and creates an
 * authenticated user in the Spring Security context.
 * The class is registered as a Spring Component to integrate with the
 * application and relies on the JwtUtil and UserService components for
 * token validation and user authentication, respectively.
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
            token =  authHeader.substring(7);
            username = jwtUtil.extractUsername(token);
                try{
                    username = jwtUtil.extractUsername(token);
                } catch (Exception e){
                    logger.error("Error extracting username from token" + e.getMessage());
                }
        }

        if(authHeader != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(username);
                if(jwtUtil.validateToken(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                    filterChain.doFilter(request, response);
        }
    }
}
