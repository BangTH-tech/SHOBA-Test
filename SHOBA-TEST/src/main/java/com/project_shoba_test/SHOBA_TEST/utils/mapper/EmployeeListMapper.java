package com.project_shoba_test.SHOBA_TEST.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.EmployeeListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmployeeListMapper implements Mapper<EmployeeListResponse, Users> {

    private final ModelMapper mapper;

    @Override
    public EmployeeListResponse mapTo(Users b) {
        return mapper.map(b, EmployeeListResponse.class);
    }

    @Override
    public Users mapFrom(EmployeeListResponse a) {
        return mapper.map(a, Users.class);
    }

}