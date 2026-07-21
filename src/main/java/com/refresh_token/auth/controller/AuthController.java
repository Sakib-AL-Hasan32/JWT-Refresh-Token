package com.refresh_token.auth.controller;

import com.refresh_token.auth.dto.request.LoginRequest;
import com.refresh_token.auth.dto.request.RegistrationRequest;
import com.refresh_token.auth.dto.response.LoginResponse;
import com.refresh_token.auth.dto.response.RegistrationResponse;
import com.refresh_token.auth.service.AuthService;
import com.refresh_token.common.constants.ApiEndpoints;
import com.refresh_token.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.Auth.BASE)
public class AuthController {
    private final AuthService authService;

    @PostMapping(ApiEndpoints.Auth.REGISTER)
    public ResponseEntity<ApiResponse<RegistrationResponse>> registration(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.registration(registrationRequest));
    }

    @PostMapping(ApiEndpoints.Auth.LOGIN)
    public ResponseEntity<ApiResponse<LoginResponse>> login (@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequest));
    }
}
