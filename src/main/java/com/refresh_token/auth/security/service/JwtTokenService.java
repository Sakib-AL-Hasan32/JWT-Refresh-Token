package com.refresh_token.auth.security.service;

import com.refresh_token.auth.entity.RefreshToken;
import com.refresh_token.auth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

public interface JwtTokenService {
    SecretKey getSecretKey();
    String getUsernameFromToken(String token);
    boolean isValidToken(String token, UserDetails userDetails);
    String generateAccessToken(User user);
    String generateRefreshToken(User user);
    RefreshToken getValidRefreshToken(String  refreshToken);
}
