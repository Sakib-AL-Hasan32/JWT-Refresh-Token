package com.refresh_token.auth.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Positive;

public record RegistrationResponse(
        @Positive
        Long id,

        @Column(nullable = false, unique = true, length = 50)
        String username,

        @Column(nullable = false, unique = true, length = 50)
        String email,

        @Column(nullable = false, length = 50)
        String password,

        @Column(nullable = false, length = 50)
        String firstName,

        @Column(nullable = false, length = 50)
        String lastName
) {
}
