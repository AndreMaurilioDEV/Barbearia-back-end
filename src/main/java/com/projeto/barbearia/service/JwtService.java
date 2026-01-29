package com.projeto.barbearia.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.projeto.barbearia.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    private final Algorithm algorithm;

    /**
     * Instantiates a new Token service.
     *
     * @param secret the secret
     */
    @Autowired
    public JwtService(@Value("${api.security.token.secret}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret);
    }

    /**
     * Generate token string.
     *
     * @param username the username
     * @return the string
     */
    public String generateToken(String username, String role) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withExpiresAt(generateExpiration())
                .sign(algorithm);
    }

    private Instant generateExpiration() {
        return Instant.now()
                .plus(2, ChronoUnit.HOURS);
    }

    /**
     * Validate token string.
     *
     * @param token the token
     * @return the string
     */
    public String validateToken(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }
}
