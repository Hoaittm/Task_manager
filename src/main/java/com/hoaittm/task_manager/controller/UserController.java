package com.hoaittm.task_manager.controller;

import com.hoaittm.task_manager.dto.request.UserCreateRequest;
import com.hoaittm.task_manager.dto.request.UserUpdateRequest;
import com.hoaittm.task_manager.dto.response.ApiResponse;
import com.hoaittm.task_manager.entity.User;
import com.hoaittm.task_manager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    List <User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("{userId}")
    User getUserById (@PathVariable("userId") String userId){
        return userService.getUserById(userId);
    }
    @PutMapping("{userId}")
    User updateUser (@PathVariable("userId") String userId , @RequestBody UserUpdateRequest request){
        return userService.updateUser(userId,request);
    }
    @DeleteMapping ("{userId}")
    User deleteUserById(@PathVariable("userId") String userId){
        return userService.deleteUser(userId);
    }
}
