package com.project_shoba_test.SHOBA_TEST.service.crawl;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.Product;

public interface CrawlService {
    public Product crawlProduct(String link);

    public void testResponse(String link);
}
