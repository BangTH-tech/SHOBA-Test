package com.project_shoba_test.SHOBA_TEST.utils.mapper.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.EditUserDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EditUserMapper implements Mapper<EditUserDto, Users> {

    private final ModelMapper mapper;

    @Override
    public EditUserDto mapTo(Users b) {
        return mapper.map(b, EditUserDto.class);
    }

    @Override
    public Users mapFrom(EditUserDto a) {
        return mapper.map(a, Users.class);
    }

}
