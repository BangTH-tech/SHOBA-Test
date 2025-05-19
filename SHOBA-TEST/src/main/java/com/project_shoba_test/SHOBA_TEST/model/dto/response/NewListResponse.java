package com.project_shoba_test.SHOBA_TEST.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewListResponse {
    private Long id;

    private String title;

    private String authorUsername;

    private String authorEmail;
}
