package com.hoaittm.task_manager.mapper;

import com.hoaittm.task_manager.dto.request.TaskRequest;
import com.hoaittm.task_manager.dto.request.UserCreateRequest;
import com.hoaittm.task_manager.dto.request.UserUpdateRequest;
import com.hoaittm.task_manager.dto.response.TaskResponse;
import com.hoaittm.task_manager.dto.response.UserResponse;
import com.hoaittm.task_manager.entity.Task;
import com.hoaittm.task_manager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toTask(TaskRequest request);

    void updateTask(@MappingTarget Task task, TaskRequest request);
    @Mapping(source = "user.id", target = "user_id")
    TaskResponse toTaskResponse(Task task);

}
