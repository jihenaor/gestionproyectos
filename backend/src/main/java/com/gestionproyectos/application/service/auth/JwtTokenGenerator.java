package com.gestionproyectos.application.service.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class JwtTokenGenerator {

    @Value("${app.security.jwt.secret}")
    private String secret;

    @Value("${app.security.jwt.expiration:57600}")
    private long expirationSeconds;

    private SecretKey getSigningKey() {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] hash = sha.digest(secretBytes);
            return Keys.hmacShaKeyFor(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible para derivar clave JWT", e);
        }
    }

    public String generateToken(String username, Set<String> roles, Map<String, Object> claims) {
        SecretKey key = getSigningKey();

        var builder = Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("name", username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationSeconds * 1000))
                .signWith(key, SignatureAlgorithm.HS256);

        if (claims != null) {
            claims.forEach(builder::claim);
        }

        return builder.compact();
    }

    public String generateToken(String username, Set<String> roles) {
        return generateToken(username, roles, null);
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}