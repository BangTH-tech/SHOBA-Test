package com.project_shoba_test.SHOBA_TEST.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.FilterEmployeeListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.EmployeeListResponse;
import com.project_shoba_test.SHOBA_TEST.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;
    
    @PostMapping("/employee-list")
    public ResponseEntity<Page<EmployeeListResponse>> register(@RequestBody @Valid FilterEmployeeListDto filterEmployeeListDto) {
        Page<EmployeeListResponse> employeeList = userService.getAllEmployees(filterEmployeeListDto);
        if(employeeList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeeList);
    }

}
