package com.refresh_token.task.dto.response;

import com.refresh_token.task.enums.TaskPriority;
import com.refresh_token.task.enums.TaskStatus;

import java.time.LocalDate;

public record TaskResponse(
        String name,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDate dueDate
) {
}
