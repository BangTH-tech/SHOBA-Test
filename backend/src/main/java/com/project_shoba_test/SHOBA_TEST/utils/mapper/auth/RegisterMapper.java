package com.project_shoba_test.SHOBA_TEST.utils.mapper.auth;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.auth.RegisterDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RegisterMapper implements Mapper<RegisterDto, Users> {

    private final ModelMapper mapper;

    @Override
    public RegisterDto mapTo(Users b) {
        return mapper.map(b, RegisterDto.class);
    }

    @Override
    public Users mapFrom(RegisterDto a) {
        return mapper.map(a, Users.class);
    }

}
