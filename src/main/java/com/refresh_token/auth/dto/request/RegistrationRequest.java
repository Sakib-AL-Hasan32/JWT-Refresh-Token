package com.refresh_token.auth.dto.request;

import jakarta.persistence.Column;

public record RegistrationRequest (
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
