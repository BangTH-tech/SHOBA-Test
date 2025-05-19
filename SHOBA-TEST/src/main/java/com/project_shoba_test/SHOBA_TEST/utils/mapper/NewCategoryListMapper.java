package com.project_shoba_test.SHOBA_TEST.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewCategoryListResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;

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
