package com.project_shoba_test.SHOBA_TEST.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HttpLogRepositoryImpl implements HttpLogRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public Page<HttpLog> searchLogs(String method, String url, Integer status, String createdBy, Pageable pageable) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (method != null && !method.isBlank()) {
            criteriaList.add(Criteria.where("method").is(method));
        }

        if (url != null && !url.isBlank()) {
            criteriaList.add(Criteria.where("url").regex(url, "i")); // contains + ignoreCase
        }

        if (status != null) {
            criteriaList.add(Criteria.where("responseStatus").is(status));
        }

        if (createdBy != null) {
            criteriaList.add(Criteria.where("createdBy").is(createdBy));
        }

        Criteria finalCriteria = criteriaList.isEmpty()
                ? new Criteria()
                : new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));

        Query query = new Query(finalCriteria).with(pageable);

        List<HttpLog> resultList = mongoTemplate.find(query, HttpLog.class);
        long total = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), HttpLog.class);

        return new PageImpl<>(resultList, pageable, total);
    }

   
}
