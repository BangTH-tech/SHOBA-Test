package com.project_shoba_test.SHOBA_TEST.service.product;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.product.FilterProductDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductListResponse;

public interface ProductService {
    public ProductDetailResponse getProductByLink(String productLink);

    public Page<ProductListResponse> getAllProduct(FilterProductDto filterProductDto);
}
