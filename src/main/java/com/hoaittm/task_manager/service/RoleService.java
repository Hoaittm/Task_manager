package com.hoaittm.task_manager.service;

import com.hoaittm.task_manager.dto.request.RoleRequest;
import com.hoaittm.task_manager.dto.response.RoleResponse;
import com.hoaittm.task_manager.mapper.RoleMapper;
import com.hoaittm.task_manager.repository.PermissionRepository;
import com.hoaittm.task_manager.repository.RoleReponsitory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleService {
    RoleReponsitory roleReponsitory;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);

        var permissions =  permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleReponsitory.save(role);
       return  roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        var roles = roleReponsitory.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role){
        roleReponsitory.deleteById(role);
    }
}
