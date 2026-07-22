package com.refresh_token.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiMessages {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Success {
        public static final String USER_REGISTERED = "User registration successful";
        public static final String USER_LOGGED_IN = "User logged in successful";
        public static final String USER_LOGGED_OUT = "User logged out successful";
        public static final String TASK_CREATED = "Task created successful";
        public static final String FETCHED_ALL_TASKS = "Fetched all tasks";

    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Error {
        public static final String USER_NOT_FOUND = "User not found";
        public static final String USER_ALREADY_EXISTS = "User already exists";
        public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
        public static final String ROLE_NOT_FOUND = "Role not found";
        public static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found";
        public static final String REFRESH_TOKEN_REVOKED = "Refresh token is revoked";
        public static final String REFRESH_TOKEN_EXPIRED = "Refresh token is expired";
        public static final String USER_INACTIVATED = "User inactivated";
        public static final String INVALID_TOKEN = "Invalid Token";
        public static final String TASK_ALREADY_EXIST = "Task already exists";
    }

}
