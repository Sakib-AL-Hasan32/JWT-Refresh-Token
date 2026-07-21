package com.refresh_token.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username Can Not Be Blanked")
        String username,

        @NotBlank(message = "Password Can Not Be Blanked")
        String password
) {
}
