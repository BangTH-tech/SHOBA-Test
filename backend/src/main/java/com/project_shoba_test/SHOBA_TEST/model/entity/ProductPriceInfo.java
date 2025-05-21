package com.project_shoba_test.SHOBA_TEST.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductPriceInfo {
    private String size;

    private Long canBookCount;

    private double price;

    private double discountPrice;

    private String color;
}
