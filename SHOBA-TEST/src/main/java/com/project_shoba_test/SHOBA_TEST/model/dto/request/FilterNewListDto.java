package com.project_shoba_test.SHOBA_TEST.model.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FilterNewListDto {
    @Min(0)
    private int page;

    @Min(3)
    private int size;

    private String sortBy;

    private boolean ascending;

    private String search;

    private Long categoryId;
}
