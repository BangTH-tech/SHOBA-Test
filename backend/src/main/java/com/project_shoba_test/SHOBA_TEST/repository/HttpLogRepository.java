package com.project_shoba_test.SHOBA_TEST.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;

public interface HttpLogRepository extends MongoRepository<HttpLog, String>, HttpLogRepositoryCustom {
 
}
