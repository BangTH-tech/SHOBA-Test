package com.project_shoba_test.SHOBA_TEST.utils.mapper.product;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.Product;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductDetailResponseMapper implements Mapper<ProductDetailResponse, Product> {

    private final ModelMapper mapper;

    @Override
    public ProductDetailResponse mapTo(Product b) {
        return mapper.map(b, ProductDetailResponse.class);
    }

    @Override
    public Product mapFrom(ProductDetailResponse a) {
        return mapper.map(a, Product.class);
    }

}