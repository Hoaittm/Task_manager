package com.hoaittm.task_manager.service;

import com.hoaittm.task_manager.dto.request.TaskRequest;
import com.hoaittm.task_manager.dto.response.TaskResponse;
import com.hoaittm.task_manager.entity.Task;
import com.hoaittm.task_manager.entity.User;
import com.hoaittm.task_manager.exception.AppException;
import com.hoaittm.task_manager.exception.ErrorCode;
import com.hoaittm.task_manager.mapper.TaskMapper;
import com.hoaittm.task_manager.repository.TaskResponsitory;
import com.hoaittm.task_manager.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class TaskService {
    TaskResponsitory taskResponsitory;
    TaskMapper taskMapper;
    UserRepository userRepository;
    public Task createTask (TaskRequest request){

        Task task  = taskMapper.toTask(request);
        task.setComplete(true);
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = jwt.getSubject(); // "sub" trong JWT chính là username

        // Tìm User theo username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Gán user vào task
        task.setUser(user);


        return taskResponsitory.save(task);
    }



    public List<TaskResponse> getTasks(){
        return taskResponsitory.findAll().stream().map(taskMapper::toTaskResponse).toList();
    }

    public List<TaskResponse> getTaskById (String id){
        List<Task> tasks = taskResponsitory.findByUserId(id);
        return tasks.stream().map(taskMapper::toTaskResponse)
                .collect(Collectors.toList());

    }

    public TaskResponse updateTask(String id,TaskRequest request){
        Task task = taskResponsitory.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.UNAUTHETICATED));

        taskMapper.updateTask(task,request);

        return taskMapper.toTaskResponse(taskResponsitory.save(task));

    }
    public Task deleteTask (String id){
        taskResponsitory.findById(id);
        return null;
    }
}
