package com.project_shoba_test.SHOBA_TEST.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PriceInfo {
    private double price;

    private int beginAmount;
}
