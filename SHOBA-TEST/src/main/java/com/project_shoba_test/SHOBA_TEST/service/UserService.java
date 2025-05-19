package com.project_shoba_test.SHOBA_TEST.service;

import org.springframework.data.domain.Page;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.FilterEmployeeListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.EmployeeListResponse;

public interface UserService {
    Page<EmployeeListResponse> getAllEmployees(FilterEmployeeListDto filterEmployeeListDto);
}
