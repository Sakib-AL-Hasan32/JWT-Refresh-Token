package com.refresh_token.task.service;

import com.refresh_token.auth.entity.User;
import com.refresh_token.auth.repository.UserRepository;
import com.refresh_token.common.constants.ApiMessages;
import com.refresh_token.common.constants.PermissionNames;
import com.refresh_token.common.response.ApiResponse;
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
}