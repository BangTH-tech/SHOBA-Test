package com.project_shoba_test.SHOBA_TEST.model.dto.response.user;

import com.project_shoba_test.SHOBA_TEST.model.enums.UserRole;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeListResponse {
    private Long id;

    private String username;

    private String fullName;

    private String email;

    private UserRole role;

    private UserStatus status;
}
