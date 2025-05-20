package com.project_shoba_test.SHOBA_TEST.utils.mapper.newCategory;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.NewCategoryListResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.news.NewListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewCategoryListMapper implements Mapper<NewCategoryListResponse, NewCategory> {

    private final ModelMapper mapper;

    @Override
    public NewCategoryListResponse mapTo(NewCategory b) {
        return mapper.map(b, NewCategoryListResponse.class);
    }

    @Override
    public NewCategory mapFrom(NewCategoryListResponse a) {
        return mapper.map(a, NewCategory.class);
    }

}
