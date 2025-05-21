package com.project_shoba_test.SHOBA_TEST.utils.mapper.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.ProfileDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProfileMapper implements Mapper<ProfileDto, Users> {

    private final ModelMapper mapper;

    @Override
    public ProfileDto mapTo(Users b) {
        return mapper.map(b, ProfileDto.class);
    }

    @Override
    public Users mapFrom(ProfileDto a) {
        return mapper.map(a, Users.class);
    }

}
