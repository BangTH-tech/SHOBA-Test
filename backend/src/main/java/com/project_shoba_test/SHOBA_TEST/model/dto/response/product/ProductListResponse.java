package com.project_shoba_test.SHOBA_TEST.model.dto.response.product;

import java.util.List;
import java.util.Set;

import com.project_shoba_test.SHOBA_TEST.model.entity.ColorInfo;
import com.project_shoba_test.SHOBA_TEST.model.entity.FeatureInfo;
import com.project_shoba_test.SHOBA_TEST.model.entity.ModelInfo;
import com.project_shoba_test.SHOBA_TEST.model.entity.PriceInfo;
import com.project_shoba_test.SHOBA_TEST.model.entity.SizeInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductListResponse {
    private Long id;

    private long canBookedAmount;

    private String title;

    private long saledCount;

    private double unitWeight;
}
