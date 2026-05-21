package com.bankinh.backend.infrastructure.security;

import com.bankinh.backend.domain.model.VaiTro;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private Key getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(UUID nguoiDungId, VaiTro vaiTro) {
        return Jwts.builder()
                .setSubject(nguoiDungId.toString())
                .claim("vaiTro", vaiTro.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public UUID extractNguoiDungId(String token) {
        return UUID.fromString(getClaims(token).getSubject());
    }

    public VaiTro extractVaiTro(String token) {
        return VaiTro.valueOf(getClaims(token).get("vaiTro", String.class));
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}