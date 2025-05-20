package com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryShortResponse {
    private long id;

    private String name;
}
