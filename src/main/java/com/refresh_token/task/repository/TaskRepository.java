package com.refresh_token.task.repository;

import com.refresh_token.auth.entity.User;
import com.refresh_token.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByName(String name);
    boolean existsByNameAndCreatedBy(String name, User createdBy);
}
