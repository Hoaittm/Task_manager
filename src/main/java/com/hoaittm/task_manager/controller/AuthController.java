package com.hoaittm.task_manager.controller;

import com.hoaittm.task_manager.dto.request.AuthRequest;
import com.hoaittm.task_manager.dto.request.IntrospectRequest;
import com.hoaittm.task_manager.dto.request.LogoutRequest;
import com.hoaittm.task_manager.dto.request.RefeshRequest;
import com.hoaittm.task_manager.dto.response.ApiResponse;
import com.hoaittm.task_manager.dto.response.AuthResponse;
import com.hoaittm.task_manager.dto.response.IntrospectResponse;
import com.hoaittm.task_manager.service.AuthService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.KeyLengthException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class AuthController {
    AuthService authService;
    @PostMapping("/token")
    ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest request) throws KeyLengthException {
        var result = authService.authenticate(request);
        return ApiResponse.<AuthResponse>builder()
                .result(result)
                .build()   ;
    }
    @PostMapping("/refresh")
    ApiResponse<AuthResponse> refeshToken(@RequestBody RefeshRequest request)
            throws JOSEException, ParseException {
        var result = authService.refreshToken(request);
        return ApiResponse.<AuthResponse>builder()
                .result(result)
                .build()   ;
    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
            var result = authService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build()   ;
    }
    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws JOSEException, ParseException {
          authService.logout(request);
        return ApiResponse.<Void>builder()

                .build()   ;
    }
}
