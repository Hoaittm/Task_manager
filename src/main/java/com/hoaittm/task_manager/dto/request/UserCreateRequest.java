package com.hoaittm.task_manager.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
@Data
public class UserCreateRequest {
    private String username;
    @Size(min = 8 , message = "INVALID_PASSWORD")
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Set<String> role;
}
