package com.project_shoba_test.SHOBA_TEST.model.entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender.Size;

@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
    private Long id;

    private String title;

    private long canBookedAmount;

    private long saledCount;

    private double unitWeight;

    private Set<String> imageUrls;

    private List<FeatureInfo> featureInfos;

    private List<ColorInfo> colorInfos;

    private List<SizeInfo> sizeInfos;

    private List<ModelInfo> modelInfos;

    private List<PriceInfo> priceInfos;
}
