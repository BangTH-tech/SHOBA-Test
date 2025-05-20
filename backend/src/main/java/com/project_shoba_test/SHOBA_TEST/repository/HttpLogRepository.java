package com.project_shoba_test.SHOBA_TEST.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;

public interface HttpLogRepository extends MongoRepository<HttpLog, String>{
    
}
