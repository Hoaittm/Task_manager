package com.hoaittm.task_manager.repository;

import com.hoaittm.task_manager.entity.InvalidatedToken;
import com.hoaittm.task_manager.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidateTokenRepository extends JpaRepository<InvalidatedToken,String> {
}
