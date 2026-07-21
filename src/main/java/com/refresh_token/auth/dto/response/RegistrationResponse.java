package com.refresh_token.auth.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Positive;

public record RegistrationResponse(
        Long id,
        String username,
        String email,
        String password,
        String firstName,
        String lastName
) {
}
