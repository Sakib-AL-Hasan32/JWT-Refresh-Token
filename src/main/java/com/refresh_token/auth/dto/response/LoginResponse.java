package com.refresh_token.auth.dto.response;

public record LoginResponse (
        String accessToken,
        String refreshToken
) {

}
