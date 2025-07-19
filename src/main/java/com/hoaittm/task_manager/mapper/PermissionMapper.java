package com.hoaittm.task_manager.mapper;

import com.hoaittm.task_manager.dto.request.PermissionRequest;
import com.hoaittm.task_manager.dto.request.UserCreateRequest;
import com.hoaittm.task_manager.dto.request.UserUpdateRequest;
import com.hoaittm.task_manager.dto.response.PermissionResponse;
import com.hoaittm.task_manager.dto.response.UserResponse;
import com.hoaittm.task_manager.entity.Permission;
import com.hoaittm.task_manager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

//    @Mapping(source = "firstName",target = "lastName")//muon firstName va lastName trung ten
    PermissionResponse toPermissionResponse(Permission permission);

}
