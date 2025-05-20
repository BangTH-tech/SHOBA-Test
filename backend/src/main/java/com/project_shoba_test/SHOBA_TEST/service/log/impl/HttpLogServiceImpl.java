package com.project_shoba_test.SHOBA_TEST.service.log.impl;

import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;
import com.project_shoba_test.SHOBA_TEST.repository.HttpLogRepository;
import com.project_shoba_test.SHOBA_TEST.service.log.HttpLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HttpLogServiceImpl implements HttpLogService{

    private final HttpLogRepository httpLogRepository;

    @Override
    public void save(HttpLog httpLog) {
        httpLogRepository.save(httpLog);
    }
    
}
