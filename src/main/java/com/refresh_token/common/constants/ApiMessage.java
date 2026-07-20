package com.refresh_token.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiMessage {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Success {

    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Error {
        public static final String USER_NOT_FOUND = "User not found";
    }

}
