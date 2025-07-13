package com.hoaittm.task_manager.controller;

import com.hoaittm.task_manager.dto.request.AuthRequest;
import com.hoaittm.task_manager.dto.response.ApiResponse;
import com.hoaittm.task_manager.dto.response.AuthResponse;
import com.hoaittm.task_manager.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthController {
    AuthService authService;
    @PostMapping("/login")
    ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest request){
        boolean result = authService.authenticate(request);
        return ApiResponse.<AuthResponse>builder()
                .result(AuthResponse.builder()
                        .authenticated(result)
                        .build())
                .build()   ;
    }
}
