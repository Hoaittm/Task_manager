package com.hoaittm.task_manager.controller;

import com.hoaittm.task_manager.dto.request.TaskRequest;
import com.hoaittm.task_manager.dto.response.ApiResponse;
import com.hoaittm.task_manager.dto.response.TaskResponse;
import com.hoaittm.task_manager.entity.Task;
import com.hoaittm.task_manager.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    ApiResponse<Task> createTask (@RequestBody @Valid TaskRequest request){
        ApiResponse<Task> apiResponse = new ApiResponse<>();
        apiResponse.setResult(taskService.createTask(request));
        return apiResponse;
    }
    @GetMapping
    List<TaskResponse> getTasks(){
        return taskService.getTasks();
    }
    @GetMapping("{userId}")

    List<TaskResponse> getTaskById(@PathVariable("userId") String userId){
        return taskService.getTaskById(userId);
    }

    @PutMapping("{id}")
    TaskResponse updateTask (@PathVariable ("id") String id, @RequestBody TaskRequest request){
        return taskService.updateTask(id,request);
    }
    @DeleteMapping("{id}")
    Task deleteTaskById(@PathVariable("id") String id){
        return taskService.deleteTask(id);
    }
}

