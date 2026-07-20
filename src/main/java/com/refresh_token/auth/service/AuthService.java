package com.refresh_token.auth.service;

import com.refresh_token.auth.dto.request.RegistrationRequest;
import com.refresh_token.auth.dto.response.RegistrationResponse;
import com.refresh_token.common.response.ApiResponse;

public interface AuthService {
    ApiResponse<RegistrationResponse>  registration(RegistrationRequest registrationRequest);
}
