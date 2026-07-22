package com.refresh_token.auth.dto.response;

import java.util.Set;

public record LoginResponse (
        String username,
        Set<String> roles,
        String accessToken,
        String refreshToken
) {

}
