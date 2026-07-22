package com.refresh_token.task.service;

import com.refresh_token.common.response.ApiResponse;
import com.refresh_token.task.dto.request.TaskRequest;
import com.refresh_token.task.dto.response.TaskResponse;
import org.springframework.security.core.userdetails.UserDetails;

public interface TaskService {
    ApiResponse<TaskResponse> createTask(TaskRequest taskRequest, UserDetails userDetails);
}
