package com.project_shoba_test.SHOBA_TEST.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;
import com.project_shoba_test.SHOBA_TEST.service.NewCategoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewsListMapper implements Mapper<NewListResponse, News> {

    private final ModelMapper mapper;


    @Override
    public NewListResponse mapTo(News b) {
        NewListResponse newListResponse = mapper.map(b, NewListResponse.class);
        newListResponse.setCategoryName(b.getCategory().getName());
        newListResponse.setAuthorUsername(b.getCreatedBy().getUsername());
        newListResponse.setAuthorEmail(b.getCreatedBy().getEmail());
        return newListResponse;
    }

    @Override
    public News mapFrom(NewListResponse a) {
        return mapper.map(a, News.class);
    }

}
