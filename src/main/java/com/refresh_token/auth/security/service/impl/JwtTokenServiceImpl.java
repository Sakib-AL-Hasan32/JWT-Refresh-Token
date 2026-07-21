package com.refresh_token.auth.security.service.impl;

import com.refresh_token.auth.entity.RefreshToken;
import com.refresh_token.auth.entity.Role;
import com.refresh_token.auth.entity.User;
import com.refresh_token.auth.repository.RefreshTokenRepository;
import com.refresh_token.auth.security.service.JwtTokenService;
import com.refresh_token.common.constants.ApiMessages;
import com.refresh_token.common.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {
    private static final int REFRESH_TOKEN_BYTES = 64;
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    private final SecureRandom secureRandom = new SecureRandom();
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret-key}")
    private String mySecretKey;

    @Value("${jwt.expiration}")
    private Duration expiration;

    @Value("${jwt.refresh-token-expiration}")
    private Duration refreshTokenExpiration;

    @Override
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(mySecretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(User user) {
        Date issuedAt = new Date();
        Date expiresAt = new Date(issuedAt.getTime() + expiration.toMillis());

        Set<String> roleNames = new LinkedHashSet<>();
        for (Role role : user.getRoles()) {
            roleNames.add(role.getName());
        }

        Map<String , Object> jwtClaims = Map.of(
                "userId", user.getId(),
                "role", roleNames
        );

        return Jwts.builder()
                .id(java.util.UUID.randomUUID().toString())
                .subject(user.getUsername())
                .claims(jwtClaims)
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(User user) {
        String rawToken = generateRawToken();
        String hashToken = generateHashToken(rawToken);
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setTokenHash(hashToken);
        refreshToken.setCreatedAt(LocalDateTime.now());
        refreshToken.setExpiresAt(LocalDateTime.now().plus(refreshTokenExpiration));
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);
        return rawToken;
    }

    private String generateRawToken() {
        byte[] tokenBytes = new byte[REFRESH_TOKEN_BYTES];
        secureRandom.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }

    private String generateHashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            char[] hexChars = new char[hash.length * 2];
            for (int i = 0; i < hash.length; i++) {
                int value = hash[i] & 0xFF;
                hexChars[i * 2] = HEX_ARRAY[value >>> 4];
                hexChars[i * 2 + 1] = HEX_ARRAY[value & 0x0F];
            }
            return new String(hexChars);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 algorithm is not available.", exception);
        }
    }

    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {
        return getUsernameFromToken(token).equals(userDetails.getUsername());
    }

    @Override
    public RefreshToken getValidRefreshToken(String rawToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenHash(generateHashToken(rawToken))
                .orElseThrow(() -> new InvalidTokenException(ApiMessages.Error.REFRESH_TOKEN_NOT_FOUND));
        if(refreshToken.isRevoked()) {
            throw new InvalidTokenException(ApiMessages.Error.REFRESH_TOKEN_REVOKED);
        }
        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new InvalidTokenException(ApiMessages.Error.REFRESH_TOKEN_EXPIRED);
        }
        if (!(refreshToken.getUser().isEnabled())) {
            throw new InvalidTokenException(ApiMessages.Error.USER_INACTIVATED);
        }
        return refreshToken;
    }

    @Override
    public String getUsernameFromToken(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    @Override
    public Instant extractExpiration(String accessToken) {
        Claims claims = extractClaims(accessToken);
        return claims.getExpiration().toInstant();
    }

    @Override
    public Boolean isTokenExpired(String bearerToken) {
        return extractExpiration(bearerToken).isBefore(Instant.now());
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String extractAccessToken(String bearerToken) {
        if(bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new InvalidTokenException(ApiMessages.Error.INVALID_TOKEN);
        }
        return bearerToken.substring(7);
    }
}
