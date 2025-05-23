package com.project_shoba_test.SHOBA_TEST.utils.mapper.product;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.Product;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductListResponseMapper implements Mapper<ProductListResponse, Product> {

    private final ModelMapper mapper;

    @Override
    public ProductListResponse mapTo(Product b) {
        return mapper.map(b, ProductListResponse.class);
    }

    @Override
    public Product mapFrom(ProductListResponse a) {
        return mapper.map(a, Product.class);
    }

}