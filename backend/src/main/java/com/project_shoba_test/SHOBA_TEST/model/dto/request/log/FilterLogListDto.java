package com.project_shoba_test.SHOBA_TEST.model.dto.request.log;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FilterLogListDto {
    @Min(0)
    private int page;

    @Min(3)
    private int size;

    private boolean ascending;

    private MethodEnum method;

    private FunctionEnum function;

    private StatusEnum status;

    private String createdBy;

}
