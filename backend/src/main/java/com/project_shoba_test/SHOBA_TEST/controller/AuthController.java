package com.project_shoba_test.SHOBA_TEST.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.auth.LoginDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.auth.RegisterDto;
import com.project_shoba_test.SHOBA_TEST.service.auth.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDto registerDto) {
        authService.register(registerDto);;
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto loginDto, HttpServletResponse response) {
        authService.login(loginDto, response);
        return ResponseEntity.noContent().build();
    }
}
