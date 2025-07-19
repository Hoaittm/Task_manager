package com.hoaittm.task_manager.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
@Data
public class UserUpdateRequest {

    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private List<String> roles  ;

}
