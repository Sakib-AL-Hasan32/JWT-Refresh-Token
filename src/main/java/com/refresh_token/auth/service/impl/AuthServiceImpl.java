package com.refresh_token.auth.service.impl;

import com.refresh_token.auth.dto.request.RegistrationRequest;
import com.refresh_token.auth.dto.response.RegistrationResponse;
import com.refresh_token.auth.entity.Role;
import com.refresh_token.auth.entity.User;
import com.refresh_token.auth.repository.RoleRepository;
import com.refresh_token.auth.repository.UserRepository;
import com.refresh_token.auth.service.AuthService;
import com.refresh_token.common.constants.ApiMessages;
import com.refresh_token.common.constants.RoleNames;
import com.refresh_token.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public ApiResponse<RegistrationResponse> registration(RegistrationRequest registrationRequest) {
        if (userRepository.existsByUsername(registrationRequest.username())) {
            throw new UsernameNotFoundException(ApiMessages.Error.USER_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(registrationRequest.email())) {
            throw new UsernameNotFoundException(ApiMessages.Error.EMAIL_ALREADY_EXISTS);
        }
        Role role = roleRepository.findByName(RoleNames.USER)
                .orElseThrow(() -> new UsernameNotFoundException(ApiMessages.Error.ROLE_NOT_FOUND));
        User user = User.builder()
                .username(registrationRequest.username())
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .firstName(registrationRequest.firstName())
                .lastName(registrationRequest.lastName())
                .roles(new LinkedHashSet<>(Set.of(role)))
                .build();
        userRepository.save(user);
        RegistrationResponse response = new RegistrationResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        );
        return ApiResponse.<RegistrationResponse>builder()
                .data(response)
                .message(ApiMessages.Success.USER_REGISTERED)
                .build();
    }
}
