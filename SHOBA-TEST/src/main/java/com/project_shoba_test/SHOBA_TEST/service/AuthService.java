package com.project_shoba_test.SHOBA_TEST.service;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.LoginDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.RegisterDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void login(LoginDto loginDto, HttpServletResponse response);
    void register(RegisterDto registerDto);
    Users getCurrentUser();
}
