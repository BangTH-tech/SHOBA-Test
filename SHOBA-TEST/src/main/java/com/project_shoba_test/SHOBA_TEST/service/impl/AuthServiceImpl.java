package com.project_shoba_test.SHOBA_TEST.service.impl;

import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.RegisterDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.repository.UserRepository;
import com.project_shoba_test.SHOBA_TEST.service.AuthService;
import com.project_shoba_test.SHOBA_TEST.utils.PasswordUtil;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.RegisterMapper;
import com.project_shoba_test.SHOBA_TEST.exception.BadRequestException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;

    private final RegisterMapper registerMapper;

    @Override
    public void register(RegisterDto registerDto) {
        if(userRepository.existsByUsernameOrEmail(registerDto.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists", "Username already exists");
        }
        if(userRepository.existsByUsernameOrEmail(registerDto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists", "Email already exists");
        }

        String encodedPassword = PasswordUtil.encodePassword(registerDto.getPassword());
        registerDto.setPassword(encodedPassword);

        Users user = registerMapper.mapFrom(registerDto);
        userRepository.save(user);
    }
    
}
