package com.project_shoba_test.SHOBA_TEST.service.log.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.log.FilterLogListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.log.HttpLogResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;
import com.project_shoba_test.SHOBA_TEST.repository.HttpLogRepository;
import com.project_shoba_test.SHOBA_TEST.service.log.HttpLogService;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.log.HttpLogResponseMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class HttpLogServiceImpl implements HttpLogService {

    private final HttpLogRepository httpLogRepository;

    private final HttpLogResponseMapper httpLogResponseMapper;

    private static final Map<String, Integer> statusText = Map.ofEntries(
        Map.entry("OK", 200),
        Map.entry("CREATED", 201),
        Map.entry("BAD_REQUEST", 400),
        Map.entry("UNAUTHORIZED", 401),
        Map.entry("NOT_FOUND", 404),
        Map.entry("FORBIDDEN", 403),
        Map.entry("NO_CONTENT", 204)
    );

    private static final Map<String, String> functionUrl = Map.ofEntries(
            Map.entry("LOGIN", "/api/v1/auth/login"),
            Map.entry("REGISTER", "/api/v1/auth/register"),
            Map.entry("EMPLOYEE_LIST", "/api/v1/admin/employee-list"),
            Map.entry("ADD_EMPLOYEE", "/api/v1/admin/add-user"),
            Map.entry("EDIT_EMPLOYEE", "/api/v1/admin/edit-user"),
            Map.entry("CHANGE_EMPLOYEE_STATUS", "/api/v1/admin/change-status"),
            Map.entry("NEWS_LIST", "/api/v1/admin/news-list"),
            Map.entry("ADD_NEWS", "/api/v1/admin/add-news"),
            Map.entry("EDIT_NEWS", "/api/v1/admin/edit-news"),
            Map.entry("DELETE_NEWS", "/api/v1/admin/delete-news"),
            Map.entry("CATEGORY_LIST", "/api/v1/admin/new-category-list"),
            Map.entry("ADD_CATEGORY", "/api/v1/admin/add-new-category"),
            Map.entry("EDIT_CATEGORY", "/api/v1/admin/edit-new-category"),
            Map.entry("DELETE_CATEGORY", "/api/v1/admin/delete-new-category"),
            Map.entry("EMPLOYEE_DETAIL", "/api/v1/admin/employee-detail"),
            Map.entry("NEWS_DETAIL", "/api/v1/admin/news-detail"),
            Map.entry("CATEGORY_DETAIL", "/api/v1/admin/new-category-detail"));

    @Override
    public void save(HttpLog httpLog) {
        httpLogRepository.save(httpLog);
    }

    @Override
    public Page<HttpLogResponse> getAllLog(FilterLogListDto filterLogListDto) {
        log.info(filterLogListDto);
        Pageable pageable = PageRequest.of(filterLogListDto.getPage(), filterLogListDto.getSize(),
                filterLogListDto.isAscending() ? Sort.by("timestamp").ascending() : Sort.by("timestamp").descending());
        log.info(filterLogListDto.getFunction() != null ? functionUrl.get(filterLogListDto.getFunction().toString()) : null);
        log.info(filterLogListDto.getStatus() != null ? statusText.get(filterLogListDto.getStatus().toString()) : null);
        Page<HttpLog> logs = httpLogRepository.searchLogs(
            filterLogListDto.getMethod().toString(),
            filterLogListDto.getFunction() != null ? functionUrl.get(filterLogListDto.getFunction().toString()) : null,
            filterLogListDto.getStatus() != null ? statusText.get(filterLogListDto.getStatus().toString()) : null,
            pageable);
        return logs.map(httpLogResponseMapper::mapTo);
    }

}
