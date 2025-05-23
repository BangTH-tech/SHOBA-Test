package com.project_shoba_test.SHOBA_TEST.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ModelInfo {
    private String modelName;

    private Long canBookCount;

    private double weight;

    private double price;

    private double discountPrice;

    private long skuId;
}
