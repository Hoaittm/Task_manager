package com.hoaittm.task_manager.service;

import com.hoaittm.task_manager.dto.request.UserCreateRequest;
import com.hoaittm.task_manager.dto.request.UserUpdateRequest;
import com.hoaittm.task_manager.dto.response.UserResponse;
import com.hoaittm.task_manager.entity.User;
import com.hoaittm.task_manager.exception.AppException;
import com.hoaittm.task_manager.exception.ErrorCode;
import com.hoaittm.task_manager.mapper.UserMapper;
import com.hoaittm.task_manager.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {

     UserRepository userRepository;

     UserMapper userMapper;
    public User createUser (UserCreateRequest request){

        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);


        return userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found")));
    };

    public UserResponse updateUser (String id,UserUpdateRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found"));
        userMapper.updateUser(user,request);

        return userMapper.toUserResponse(userRepository.save(user));
    }
    public User deleteUser(String userId){
        userRepository.deleteById(userId);
        return null;
    }
}
