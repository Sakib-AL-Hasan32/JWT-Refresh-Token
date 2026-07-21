package com.refresh_token.auth.dto.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}
