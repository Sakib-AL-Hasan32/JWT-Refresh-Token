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
        public static final String REFRESH = "/refresh";
        public static final String LOGOUT = "/logout";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Task {
        public static final String BASE = API_VERSION + "/task";
        public static final String CREATE = "/create";
        public static final String GET_ALL = "/getAll";
        public static final String GET_BY_NAME = "/getByName";
        public static final String UPDATE = "/update/{id}";
    }

}
