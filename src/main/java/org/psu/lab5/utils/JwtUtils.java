package org.psu.lab5.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

import javax.crypto.SecretKey;

import org.psu.lab5.model.User;
import org.psu.lab5.authentication.JwtAuthentication;
import org.psu.lab5.model.Role;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.NonNull;

@Component
public class JwtUtils {
    private final SecretKey jwtSecret;
    private final int jwtExpritationSeconds;

    public JwtUtils(
            @Value("${lab5.jwtSecret}") String secret,
            @Value("${lab5.jwtExpirationSeconds}") int expiration) {
        this.jwtSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtExpritationSeconds = expiration;
    }

    public String generate(@NonNull User user) {
        final LocalDateTime timeNow = LocalDateTime.now();
        final Instant expirationInstant = timeNow.plusSeconds(this.jwtExpritationSeconds).atZone(ZoneId.systemDefault())
                .toInstant();
        final Date expiration = Date.from(expirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(expiration)
                .signWith(this.jwtSecret)
                .claim("roles", user.getRoles())
                .claim("username", user.getUsername())
                .compact();
    }

    public boolean validate(@NonNull String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(this.jwtSecret)
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | SignatureException e) {
            return false;
        }
    }

    public Claims extractClaims(@NonNull String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static JwtAuthentication generateAuth(@NonNull Claims claims) {
        JwtAuthentication jwtAuth = new JwtAuthentication();
        jwtAuth.setRoles(extractRoles(claims));
        jwtAuth.setUsername(claims.get("username", String.class));
        return jwtAuth;

    }

    private static Set<Role> extractRoles(@NonNull Claims claims) {
        List<String> roles = claims.get("roles", List.class);
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

}
