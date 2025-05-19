package com.project_shoba_test.SHOBA_TEST.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.RegisterDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;

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
