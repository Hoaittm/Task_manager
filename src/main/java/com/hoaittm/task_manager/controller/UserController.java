package com.hoaittm.task_manager.controller;

import com.hoaittm.task_manager.dto.request.UserCreateRequest;
import com.hoaittm.task_manager.dto.request.UserUpdateRequest;
import com.hoaittm.task_manager.dto.response.ApiResponse;
import com.hoaittm.task_manager.dto.response.UserResponse;
import com.hoaittm.task_manager.entity.User;
import com.hoaittm.task_manager.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController

@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
     ApiResponse<User> createUser (@RequestBody @Valid UserCreateRequest request){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    List <UserResponse> getUsers(){
       var authentication =  SecurityContextHolder.getContext().getAuthentication();

       log.info("Username: {}",authentication.getName());
       authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return userService.getUsers();
    }

    @GetMapping("{userId}")
    UserResponse getUserById (@PathVariable("userId") String userId){
        return userService.getUserById(userId);
    }
    @GetMapping("/myinfo")
    UserResponse getMyInfo (){
        return userService.getMyInfo();
    }
    @PutMapping("{userId}")
    UserResponse updateUser (@PathVariable("userId") String userId , @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId,request);
    }
    @DeleteMapping ("{userId}")
    User deleteUserById(@PathVariable("userId") String userId){
        return userService.deleteUser(userId);
    }
}
