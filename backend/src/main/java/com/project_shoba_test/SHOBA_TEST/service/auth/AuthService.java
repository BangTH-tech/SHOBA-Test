package com.project_shoba_test.SHOBA_TEST.service.auth;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.auth.LoginDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.auth.RegisterDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.ProfileDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;

import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    ProfileDto login(LoginDto loginDto, HttpServletResponse response);
    void register(RegisterDto registerDto);
    void logout(HttpServletResponse response);
    Users getCurrentUser();
}
