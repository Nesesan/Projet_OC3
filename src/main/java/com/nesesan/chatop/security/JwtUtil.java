package com.nesesan.chatop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * Utility class for managing and validating JSON Web Tokens (JWT) for authentication and authorization.
 *
 * This class provides helper methods to generate tokens, validate them, and extract claims or other
 * information such as username and expiration from the tokens.
 *
 * Key functionalities:
 * - Token generation: Creates a JWT token for a given username with a predefined expiration period.
 * - Token validation: Validates a token against a provided username and checks if the token is expired.
 * - Claims extraction: Provides methods to extract specific claims or all claims from a token.
 * - Utility methods to extract specific information from a token, such as the username or expiration date.
 *
 * Dependencies:
 * - Relies on a secret key (`SECRET_KEY`) for signing and verifying tokens.
 *
 * Thread Safety:
 * - This class is designed to be thread-safe in typical use cases since it does not maintain state
 *   across method calls apart from the injected secret key.
 *
 * Usage:
 * This utility is commonly used in JWT-based authentication workflows, such as in conjunction
 * with authentication filters or controllers handling login endpoints.
 *
 * Limitations:
 * - Tokens generated with this utility cannot be invalidated without a mechanism for tracking
 *   and revoking tokens (e.g., maintaining a blacklist).
 */
@Component
public class JwtUtil {
    @Value( "${jwt.secret}")
    private  String SECRET_KEY ;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getSignKey())
                .compact();
    }


    public boolean validateToken(String token, String username) {
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
