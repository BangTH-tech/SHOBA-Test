package com.project_shoba_test.SHOBA_TEST.model.dto.request.product;

import com.project_shoba_test.SHOBA_TEST.model.entity.ColorInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ColorCrawl {
    private long fid;

    private String prop;
    
    private Object value;
}
