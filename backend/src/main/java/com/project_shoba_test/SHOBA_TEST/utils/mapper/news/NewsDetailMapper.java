package com.project_shoba_test.SHOBA_TEST.utils.mapper.news;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.news.NewDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewsDetailMapper implements Mapper<NewDetailResponse, News> {

    private final ModelMapper mapper;


    @Override
    public NewDetailResponse mapTo(News b) {
        NewDetailResponse newListResponse = mapper.map(b, NewDetailResponse.class);
        newListResponse.setCategoryName(b.getCategory().getName());
        newListResponse.setAuthorUsername(b.getCreatedBy().getUsername());
        newListResponse.setAuthorEmail(b.getCreatedBy().getEmail());
        return newListResponse;
    }

    @Override
    public News mapFrom(NewDetailResponse a) {
        return mapper.map(a, News.class);
    }

}

