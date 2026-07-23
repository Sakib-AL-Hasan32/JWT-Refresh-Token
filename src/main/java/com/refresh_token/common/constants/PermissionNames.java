package com.refresh_token.common.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PermissionNames {
    public static final String PROFILE_READ = "PROFILE_READ";
    public static final String CREATE_TASK = "CREATE_TASK";
    public static final String GET_ALL_TASK = "GET_ALL_TASK";
    public static final String GET_TASK_BY_NAME = "GET_TASK_BY_NAME";
    public static final String UPDATE_TASK = "UPDATE_TASK";
    public static final String DELETE_TASK = "DELETE_TASK";
}
