package com.project_shoba_test.SHOBA_TEST.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project_shoba_test.SHOBA_TEST.model.entity.Product;

public interface ProductRepository extends MongoRepository<Product, Long> {
 
}
