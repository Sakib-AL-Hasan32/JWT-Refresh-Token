package com.refresh_token.task.service;

import com.refresh_token.auth.entity.User;
import com.refresh_token.auth.repository.UserRepository;
import com.refresh_token.common.constants.ApiMessages;
import com.refresh_token.common.constants.PermissionNames;
import com.refresh_token.common.response.ApiResponse;
import com.refresh_token.task.dto.request.FindByName;
import com.refresh_token.task.dto.request.TaskRequest;
import com.refresh_token.task.dto.response.TaskResponse;
import com.refresh_token.task.entity.Task;
import com.refresh_token.task.enums.TaskStatus;
import com.refresh_token.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasAuthority('" + PermissionNames.CREATE_TASK + "')")
    public ApiResponse<TaskResponse> createTask(TaskRequest taskRequest, UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ApiMessages.Error.USER_NOT_FOUND));
        if (taskRepository.existsByNameAndCreatedBy(taskRequest.name(), user)) {
            throw new IllegalArgumentException(ApiMessages.Error.TASK_ALREADY_EXIST);
        }
        Task task = Task.builder()
                .name(taskRequest.name())
                .description(taskRequest.description())
                .status(TaskStatus.TODO)
                .priority(taskRequest.priority())
                .dueDate(taskRequest.dueDate())
                .createdBy(user)
                .build();
        taskRepository.save(task);
        TaskResponse response = new TaskResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );
        return ApiResponse.<TaskResponse>builder()
                .data(response)
                .message(ApiMessages.Success.TASK_CREATED)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('" + PermissionNames.GET_ALL_TASK + "')")
    public ApiResponse<List<TaskResponse>> getAllTask(UserDetails userDetails) {
        List<Task> tasks = taskRepository.findAllByCreatedByUsername(userDetails.getUsername());
        if(tasks.isEmpty()) {
            return ApiResponse.<List<TaskResponse>>builder()
                    .data(Collections.emptyList())
                    .message(ApiMessages.Error.NO_TASK_FOUND)
                    .build();
        }
        List<TaskResponse> response = new ArrayList<>();
        for (Task task : tasks) {
            response.add(new TaskResponse(
                    task.getId(),
                    task.getName(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getPriority(),
                    task.getDueDate()
            ));
        }
        return ApiResponse.<List<TaskResponse>>builder()
                .data(response)
                .message(ApiMessages.Success.FETCHED_ALL_TASKS)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('" + PermissionNames.GET_TASK_BY_NAME + "')")
    public ApiResponse<List<TaskResponse>> getTaskByName(UserDetails userDetails, FindByName taskName) {
        List<Task> tasks = taskRepository.findByCreatedByUsernameAndNameContainingIgnoreCase(userDetails.getUsername(), taskName.name());
        if (tasks.isEmpty()) {
            return ApiResponse.<List<TaskResponse>>builder()
                    .data(Collections.emptyList())
                    .message(ApiMessages.Error.TASK_NOT_FOUND)
                    .build();
        }
        List<TaskResponse> response = new ArrayList<>();
        for (Task task : tasks) {
            response.add(new TaskResponse(
                    task.getId(),
                    task.getName(),
                    task.getDescription(),
                    task.getStatus(),
                    task.getPriority(),
                    task.getDueDate()
            ));
        }
        return ApiResponse.<List<TaskResponse>>builder()
                .data(response)
                .message(ApiMessages.Success.FETCHED_TASK_BY_NAME)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('" + PermissionNames.UPDATE_TASK + "')")
    public ApiResponse<TaskResponse> updateTask(UserDetails userDetails, TaskRequest taskRequest, Long id) {
        Task task = taskRepository.findByCreatedByUsernameAndId(userDetails.getUsername(), id)
                .orElseThrow(() -> new IllegalArgumentException(ApiMessages.Error.TASK_NOT_FOUND));
        task.setName(taskRequest.name());
        task.setDescription(taskRequest.description());
        task.setPriority(taskRequest.priority());
        task.setDueDate(taskRequest.dueDate());
        taskRepository.save(task);
        TaskResponse response = new TaskResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate()
        );
        return ApiResponse.<TaskResponse>builder()
                .data(response)
                .message(ApiMessages.Success.TASK_UPDATED)
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('" + PermissionNames.DELETE_TASK + "')")
    public ApiResponse<TaskResponse> deleteTask(UserDetails userDetails, Long id) {
        Task task = taskRepository.findByCreatedByUsernameAndId(userDetails.getUsername(), id)
                .orElseThrow(() -> new IllegalArgumentException(ApiMessages.Error.TASK_NOT_FOUND));
        taskRepository.delete(task);
        return ApiResponse.<TaskResponse>builder()
                .message(ApiMessages.Success.TASK_DELETED)
                .build();
    }
}