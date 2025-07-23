package com.hoaittm.task_manager.repository;

import com.hoaittm.task_manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskResponsitory extends JpaRepository<Task,String> {
    List<Task> findByUserId(String userId);
}
