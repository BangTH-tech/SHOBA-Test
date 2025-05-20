package com.project_shoba_test.SHOBA_TEST.utils.mapper.newCategory;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.CategoryShortResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoryShortResponseMapper implements Mapper<CategoryShortResponse, NewCategory> {

    private final ModelMapper mapper;

    @Override
    public CategoryShortResponse mapTo(NewCategory b) {
        return mapper.map(b, CategoryShortResponse.class);
    }

    @Override
    public NewCategory mapFrom(CategoryShortResponse a) {
        return mapper.map(a, NewCategory.class);
    }

}
