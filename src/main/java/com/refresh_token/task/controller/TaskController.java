package com.refresh_token.task.controller;

import com.refresh_token.common.constants.ApiEndpoints;
import com.refresh_token.common.response.ApiResponse;
import com.refresh_token.task.dto.request.TaskRequest;
import com.refresh_token.task.dto.response.TaskResponse;
import com.refresh_token.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiEndpoints.Task.BASE)
public class TaskController {
    private final TaskService taskService;

    @PostMapping(ApiEndpoints.Task.CREATE)
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@Valid @RequestBody TaskRequest taskRequest,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskRequest, userDetails));
    }
}
