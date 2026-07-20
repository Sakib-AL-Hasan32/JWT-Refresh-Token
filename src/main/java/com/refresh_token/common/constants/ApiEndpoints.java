package com.refresh_token.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiEndpoints {
    public static final String API_VERSION = "/api/v1";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Auth {
        public static final String BASE = API_VERSION + "/auth";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Task {
        public static final String BASE = API_VERSION + "/task";
        public static final String CREATE = "/create";
    }

}
