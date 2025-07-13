package com.hoaittm.task_manager.mapper;

import com.hoaittm.task_manager.dto.request.UserCreateRequest;
import com.hoaittm.task_manager.dto.request.UserUpdateRequest;
import com.hoaittm.task_manager.dto.response.UserResponse;
import com.hoaittm.task_manager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
//    @Mapping(source = "firstName",target = "lastName")//muon firstName va lastName trung ten
    UserResponse toUserResponse(User user);

}
