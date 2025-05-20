package com.project_shoba_test.SHOBA_TEST.utils.mapper.user;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.user.EmployeeDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeDetailMapper implements Mapper<EmployeeDetailResponse, Users> {

    private final ModelMapper mapper;

    @Override
    public EmployeeDetailResponse mapTo(Users b) {
        return mapper.map(b, EmployeeDetailResponse.class);
    }

    @Override
    public Users mapFrom(EmployeeDetailResponse a) {
        return mapper.map(a, Users.class);
    }

}
