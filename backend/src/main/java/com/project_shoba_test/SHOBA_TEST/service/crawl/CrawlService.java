package com.project_shoba_test.SHOBA_TEST.service.crawl;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductDetailResponse;

public interface CrawlService {
    public ProductDetailResponse crawlProduct(String link);
}
