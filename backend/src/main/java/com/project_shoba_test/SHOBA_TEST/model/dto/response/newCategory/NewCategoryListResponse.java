package com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.project_shoba_test.SHOBA_TEST.utils.serializer.TimestampSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewCategoryListResponse {
    private Long id;

    private String name;

    private String creatorName;

    private String creatorEmail;
}
