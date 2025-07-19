package com.hoaittm.task_manager.repository;

import com.hoaittm.task_manager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleReponsitory extends JpaRepository<Role,String> {
}
