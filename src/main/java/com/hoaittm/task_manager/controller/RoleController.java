package com.hoaittm.task_manager.controller;

import com.hoaittm.task_manager.dto.request.PermissionRequest;
import com.hoaittm.task_manager.dto.request.RoleRequest;
import com.hoaittm.task_manager.dto.response.ApiResponse;
import com.hoaittm.task_manager.dto.response.PermissionResponse;
import com.hoaittm.task_manager.dto.response.RoleResponse;
import com.hoaittm.task_manager.service.PermissionService;
import com.hoaittm.task_manager.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }
    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(){
        return  ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }
    @DeleteMapping("/{role}")
    ApiResponse<Void> delete (@PathVariable String role){
        roleService.delete(role);
        return ApiResponse.<Void>builder()
                .build();
    }

}
