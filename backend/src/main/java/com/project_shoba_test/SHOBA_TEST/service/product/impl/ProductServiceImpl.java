package com.project_shoba_test.SHOBA_TEST.service.product.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_shoba_test.SHOBA_TEST.exception.NotFoundException;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.product.FilterProductDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.CategoryShortResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.model.entity.Product;
import com.project_shoba_test.SHOBA_TEST.repository.ProductRepository;
import com.project_shoba_test.SHOBA_TEST.service.crawl.CrawlService;
import com.project_shoba_test.SHOBA_TEST.service.product.ProductService;
import com.project_shoba_test.SHOBA_TEST.service.redis.RedisService;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.product.ProductDetailResponseMapper;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.product.ProductListResponseMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final RedisService redisService;

    private final CrawlService crawlService;

    private final ProductRepository productRepository;

    private final ProductDetailResponseMapper productDetailResponseMapper;

    private final ProductListResponseMapper productListResponseMapper;

    @Override
    public ProductDetailResponse getProductByLink(String productLink) {

        Long productId = extractIdFromLink(productLink);
        Product product;
        if (redisService.getProduct(productId) == null) {
            if (productRepository.existsById(productId)) {
                product = productRepository.findById(productId).orElse(null);
            } else {
                product = crawlService.crawlProduct(productLink);
                if(product == null) {
                    throw new NotFoundException("Product not found", "Product not found");
                }
                productRepository.save(product);
            }
            redisService.saveProduct(product);
        } else {
            product = redisService.getProduct(productId);

        }
        return productDetailResponseMapper.mapTo(product);
    }

    private Long extractIdFromLink(String link) {
        String productId = link.replaceAll("https://m.1688.com/offer/", "");
        productId = productId.replaceAll("\\.html", "");
        return Long.parseLong(productId);
    }

    @Override
    public Page<ProductListResponse> getAllProduct(FilterProductDto filterProductDto) {
        Pageable pageable = PageRequest.of(filterProductDto.getPage(), filterProductDto.getSize());
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productListResponseMapper::mapTo);
    }

}
