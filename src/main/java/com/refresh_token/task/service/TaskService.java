package com.refresh_token.task.service;

import com.refresh_token.common.response.ApiResponse;
import com.refresh_token.task.dto.request.FindByName;
import com.refresh_token.task.dto.request.TaskRequest;
import com.refresh_token.task.dto.response.TaskResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface TaskService {
    ApiResponse<TaskResponse> createTask(TaskRequest taskRequest, UserDetails userDetails);
    ApiResponse<List<TaskResponse>> getAllTask(UserDetails userDetails);
    ApiResponse<List<TaskResponse>> getTaskByName(UserDetails userDetails, FindByName taskName);
    ApiResponse<TaskResponse> updateTask(UserDetails userDetails, TaskRequest taskRequest, Long id);
    ApiResponse<TaskResponse> deleteTask(UserDetails userDetails, Long id);
}
