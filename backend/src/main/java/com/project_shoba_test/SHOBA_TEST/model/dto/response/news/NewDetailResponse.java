package com.project_shoba_test.SHOBA_TEST.model.dto.response.news;

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
public class NewDetailResponse {
    private Long id;

    private String title;

    private String content;

    private Long categoryId;

    private String categoryName;

    private String authorUsername;

    private String authorEmail;

    @JsonSerialize(using = TimestampSerializer.class)
    private Long createdAt;
}
