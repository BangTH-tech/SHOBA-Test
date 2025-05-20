package com.project_shoba_test.SHOBA_TEST.utils.mapper.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.AddUserDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddUserMapper implements Mapper<AddUserDto, Users> {

    private final ModelMapper mapper;

    @Override
    public AddUserDto mapTo(Users b) {
        return mapper.map(b, AddUserDto.class);
    }

    @Override
    public Users mapFrom(AddUserDto a) {
        return mapper.map(a, Users.class);
    }

}
