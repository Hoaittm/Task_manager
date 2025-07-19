package com.hoaittm.task_manager.service;

import com.hoaittm.task_manager.dto.request.UserCreateRequest;
import com.hoaittm.task_manager.dto.request.UserUpdateRequest;
import com.hoaittm.task_manager.dto.response.UserResponse;
import com.hoaittm.task_manager.entity.User;
import com.hoaittm.task_manager.enums.Role;
import com.hoaittm.task_manager.exception.AppException;
import com.hoaittm.task_manager.exception.ErrorCode;
import com.hoaittm.task_manager.mapper.UserMapper;
import com.hoaittm.task_manager.repository.RoleReponsitory;
import com.hoaittm.task_manager.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class UserService {

     UserRepository userRepository;
RoleReponsitory roleReponsitory;
     UserMapper userMapper;
     PasswordEncoder passwordEncoder;
    public User createUser (UserCreateRequest request){

        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);
        User user = userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
    //user.setRoles(roles);
        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers(){
        log.info("In method get Users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(String id){
        log.info("In method get user bt Id");
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found")));
    };

    public UserResponse updateUser (String id,UserUpdateRequest request){
        User user = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found"));
        userMapper.updateUser(user,request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleReponsitory.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        return userMapper.toUserResponse(userRepository.save(user));
    }
    public User deleteUser(String userId){
        userRepository.deleteById(userId);
        return null;
    }
    public UserResponse getMyInfo(){
      var context =  SecurityContextHolder.getContext();
      String name = context.getAuthentication().getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));

        return userMapper.toUserResponse(user);
    }
}
