package com.refresh_token.task.dto.request;

import com.refresh_token.task.enums.TaskPriority;
import jakarta.persistence.Column;

import java.time.LocalDate;

public record TaskRequest(
        @Column(nullable = false, unique = true, length = 50)
        String name,

        @Column(nullable = false, length = 50)
        String description,

        @Column(nullable = false, length = 50)
        TaskPriority priority,

        @Column(nullable = false, length = 50)
        LocalDate dueDate
) {
}
