package com.project_shoba_test.SHOBA_TEST.service;

import org.springframework.data.domain.Page;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.FilterEmployeeListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.EmployeeListResponse;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserRole;

public interface UserService {
    Page<EmployeeListResponse> getAllEmployees(FilterEmployeeListDto filterEmployeeListDto);

    void addUser(AddUserDto addUserDto);  
    
    void editUser(EditUserDto editUserDto);  

    void changeUserStatus(Long id);
}
