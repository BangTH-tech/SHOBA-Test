package com.project_shoba_test.SHOBA_TEST.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.LoginDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.RegisterDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserRole;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserStatus;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;
import com.project_shoba_test.SHOBA_TEST.service.AuthService;
import com.project_shoba_test.SHOBA_TEST.service.RecaptchaVerifierService;
import com.project_shoba_test.SHOBA_TEST.service.TokenService;
import com.project_shoba_test.SHOBA_TEST.utils.PasswordUtil;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.RegisterMapper;

import jakarta.servlet.http.HttpServletResponse;

import com.project_shoba_test.SHOBA_TEST.exception.BadRequestException;
import com.project_shoba_test.SHOBA_TEST.exception.NotFoundException;
import com.project_shoba_test.SHOBA_TEST.exception.UnauthorizedException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final RegisterMapper registerMapper;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final RecaptchaVerifierService recaptchaVerifierService;

    @Override
    public void register(RegisterDto registerDto) {
        if(verifyCaptchaToken(registerDto.getRecaptchaToken()) == false) {
            throw new BadRequestException("Captcha verification failed", "Captcha verification failed");
        }
        if (userRepository.existsByUsernameOrEmail(registerDto.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists", "Username already exists");
        }
        if (userRepository.existsByUsernameOrEmail(registerDto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists", "Email already exists");
        }

        String encodedPassword = PasswordUtil.encodePassword(registerDto.getPassword());
        registerDto.setPassword(encodedPassword);

        Users user = registerMapper.mapFrom(registerDto);
        user.setRole(UserRole.GUEST);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    private boolean verify(LoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        return authentication.isAuthenticated();
    }

    @Override
    public void login(LoginDto loginDto, HttpServletResponse response) {
        verify(loginDto);
        if(verifyCaptchaToken(loginDto.getRecaptchaToken()) == false) {
            throw new BadRequestException("Captcha verification failed", "Captcha verification failed");
        }
        
        String username = loginDto.getUsername();

        Users user = userRepository.existsByUsernameOrEmail(username)
                .orElseThrow(() -> new NotFoundException("User not found", "User not found"));
        
        if (user.getStatus() == UserStatus.INACTIVE) {
            throw new UnauthorizedException("User is inactive", "User is inactive");
        }

        String accessToken = tokenService.generateAccessToken(user.getUsername());
        tokenService.addTokenToCookie(accessToken, response);
    }

    private boolean verifyCaptchaToken(String captchaToken) {
        //return recaptchaVerifierService.verifyToken(captchaToken);
        return true; 
    }

}
