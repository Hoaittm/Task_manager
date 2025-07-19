package com.hoaittm.task_manager.mapper;

import com.hoaittm.task_manager.dto.request.RoleRequest;

import com.hoaittm.task_manager.dto.response.RoleResponse;
import com.hoaittm.task_manager.entity.Role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface RoleMapper {
    @Mapping(target = "permissions",ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}