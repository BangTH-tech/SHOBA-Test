package com.project_shoba_test.SHOBA_TEST.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditUserDto;
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
    public ResponseEntity<Page<EmployeeListResponse>> getEmployeeList(@RequestBody @Valid FilterEmployeeListDto filterEmployeeListDto) {
        Page<EmployeeListResponse> employeeList = userService.getAllEmployees(filterEmployeeListDto);
        if(employeeList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody @Valid AddUserDto addUserDto) {
        userService.addUser(addUserDto);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/edit-user")
    public ResponseEntity<?> editser(@RequestBody @Valid EditUserDto editUserDto) {
        userService.editUser(editUserDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/change-status/{userId}")
    public ResponseEntity<?> changeUserStatus(@PathVariable Long userId) {
        userService.changeUserStatus(userId);
        return ResponseEntity.noContent().build();
    }
}
