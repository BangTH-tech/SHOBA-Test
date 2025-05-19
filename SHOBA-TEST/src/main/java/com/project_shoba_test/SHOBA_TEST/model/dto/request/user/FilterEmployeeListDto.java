package com.project_shoba_test.SHOBA_TEST.model.dto.request.user;

import com.project_shoba_test.SHOBA_TEST.model.enums.UserRole;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FilterEmployeeListDto {
    @Min(0)
    private int page;

    @Min(3)
    private int size;

    private String sortBy;

    private boolean ascending;

    private String search;

    private UserRole role;

    private UserStatus status;
}
