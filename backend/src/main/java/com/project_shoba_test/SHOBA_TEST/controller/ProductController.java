package com.project_shoba_test.SHOBA_TEST.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductDetailResponse;
import com.project_shoba_test.SHOBA_TEST.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/detail")
    public ResponseEntity<ProductDetailResponse> getProductByLink(@RequestParam String link) {
        return ResponseEntity.ok(productService.getProductByLink(link));
    }

    
}
