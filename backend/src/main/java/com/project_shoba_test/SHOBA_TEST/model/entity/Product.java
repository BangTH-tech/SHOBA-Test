package com.project_shoba_test.SHOBA_TEST.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
    private Long id;

    private String title;

    private long saledCount;

    private String[] imageUrls;

    private FeatureInfo[] featureInfos;

    private ColorInfo[] colorInfos;

    private ProductPriceInfo[] productPriceInfos;
}
