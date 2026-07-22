package com.refresh_token.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token can not be blanked")
        String refreshToken,

        @NotBlank(message = "Access token can not be blanked")
        String accessToken
) {
}
