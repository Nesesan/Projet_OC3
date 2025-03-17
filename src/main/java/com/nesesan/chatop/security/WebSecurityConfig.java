package com.nesesan.chatop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebSecurityConfig is a Spring Security configuration class that customizes
 * security settings for the application.
 *
 * This class configures authentication, session management, and endpoint access rules
 * to ensure secure communication. Additionally, it incorporates a custom JWT-based
 * authentication mechanism using the JwtAuthenticationFilter.
 *
 * Main Responsibilities:
 * - Configures HTTP security rules, such as disabling CSRF protection and setting
 *   stateless session management.
 * - Defines authorization rules for HTTP requests, permitting or restricting access
 *   to endpoints based on their roles or permissions.
 * - Integrates a JWT authentication filter into the Spring Security filter chain to
 *   validate and process authentication tokens.
 * - Provides a PasswordEncoder bean for secure password hashing and a custom
 *   AuthenticationManager for handling authentication operations.
 *
 * Key Components:
 * - JwtAuthenticationFilter: A custom filter for authenticating incoming requests based
 *   on JSON Web Tokens.
 * - PasswordEncoder: Provides an implementation of BCryptPasswordEncoder for encoding
 *   and validating passwords.
 *
 * Configuration Details:
 * - CSRF protection is disabled for stateless authentication.
 * - Publicly accessible routes include API documentation endpoints as well as
 *   authentication-specific endpoints such as register and login.
 * - All other endpoints require authenticated access.
 *
 * Usage:
 * This class is typically included as part of the application's Spring Security setup
 * and is used to enforce security best practices in conjunction with the JwtAuthenticationFilter
 * and JwtUtil.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public WebSecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/auth/register",
                                "/api/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

       return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws  Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

}
