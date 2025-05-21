package com.project_shoba_test.SHOBA_TEST.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ColorInfo {
    private String colorName;

    private String imageUrl;
}
