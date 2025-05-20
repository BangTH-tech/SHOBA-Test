package com.project_shoba_test.SHOBA_TEST.service.log;

import org.springframework.data.domain.Page;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.log.FilterLogListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.log.HttpLogResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;

public interface HttpLogService {
    public void save(HttpLog httpLog);

    public Page<HttpLogResponse> getAllLog(FilterLogListDto filterLogListDto);
}
