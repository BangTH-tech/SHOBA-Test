package com.project_shoba_test.SHOBA_TEST.service.redis;

import com.project_shoba_test.SHOBA_TEST.model.entity.Product;

public interface RedisService {
    Product getProduct(Long productId);

    void saveProduct(Product product);
}
