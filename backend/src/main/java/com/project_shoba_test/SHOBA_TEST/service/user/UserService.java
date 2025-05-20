package com.project_shoba_test.SHOBA_TEST.service.user;

import org.springframework.data.domain.Page;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.AddUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.EditUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.FilterEmployeeListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.user.EmployeeDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.user.EmployeeListResponse;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserRole;

public interface UserService {
    Page<EmployeeListResponse> getAllEmployees(FilterEmployeeListDto filterEmployeeListDto);

    void addUser(AddUserDto addUserDto);  
    
    void editUser(EditUserDto editUserDto);  

    void changeUserStatus(Long id);

    EmployeeDetailResponse getEmployeeDetailResponse(Long id);
}
