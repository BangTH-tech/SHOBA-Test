package com.project_shoba_test.SHOBA_TEST.utils.mapper.log;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.log.HttpLogResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.CategoryShortResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.HttpLog;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HttpLogResponseMapper implements Mapper<HttpLogResponse, HttpLog> {

    private final ModelMapper mapper;


    @Override
    public HttpLogResponse mapTo(HttpLog b) {
        return mapper.map(b, HttpLogResponse.class);
    }

    @Override
    public HttpLog mapFrom(HttpLogResponse a) {
        return mapper.map(a, HttpLog.class);
    }

}