package com.refresh_token.auth.service;

import com.refresh_token.auth.dto.request.LoginRequest;
import com.refresh_token.auth.dto.request.RefreshTokenRequest;
import com.refresh_token.auth.dto.request.RegistrationRequest;
import com.refresh_token.auth.dto.response.LoginResponse;
import com.refresh_token.auth.dto.response.RefreshTokenResponse;
import com.refresh_token.auth.dto.response.RegistrationResponse;
import com.refresh_token.common.response.ApiResponse;

public interface AuthService {
    ApiResponse<RegistrationResponse>  registration(RegistrationRequest registrationRequest);
    ApiResponse<LoginResponse> login(LoginRequest loginRequest);
    ApiResponse<RefreshTokenResponse> refresh(RefreshTokenRequest refreshTokenRequest);
    ApiResponse<Void> logout(String bearerToken, RefreshTokenRequest refreshTokenRequest);
}
