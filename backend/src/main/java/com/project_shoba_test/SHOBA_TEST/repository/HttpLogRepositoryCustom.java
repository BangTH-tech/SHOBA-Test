package com.project_shoba_test.SHOBA_TEST.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;

public interface HttpLogRepositoryCustom {
    Page<HttpLog> searchLogs(String method, String url, Integer status, Pageable pageable);
}
