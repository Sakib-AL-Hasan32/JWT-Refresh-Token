package com.refresh_token.auth.security.service.impl;

import com.refresh_token.auth.security.service.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {
    @Value("${jwt.secret-key}")
    private String mySecretKey;

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(mySecretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {
        return getUsernameFromToken(token).equals(userDetails.getUsername());
    }
}
