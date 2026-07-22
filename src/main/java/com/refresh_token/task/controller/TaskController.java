package com.refresh_token.task.controller;

import com.refresh_token.common.constants.ApiEndpoints;
import com.refresh_token.common.response.ApiResponse;
import com.refresh_token.task.dto.request.FindByName;
import com.refresh_token.task.dto.request.TaskRequest;
import com.refresh_token.task.dto.response.TaskResponse;
import com.refresh_token.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(ApiEndpoints.Task.GET_ALL)
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAllTasks(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getAllTask(userDetails));
    }

    @GetMapping(ApiEndpoints.Task.GET_BY_NAME)
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getTaskByName(@AuthenticationPrincipal UserDetails userDetails,
                                                                         @Valid @RequestBody FindByName taskName) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskByName(userDetails, taskName));
    }

    @PostMapping(ApiEndpoints.Task.UPDATE)
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(@AuthenticationPrincipal UserDetails userDetails,
                                                                @PathVariable Long id,
                                                                @Valid @RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(userDetails, taskRequest, id));
    }
}
