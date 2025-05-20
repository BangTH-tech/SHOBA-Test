package com.project_shoba_test.SHOBA_TEST.utils.mapper.newCategory;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.NewCategoryDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NewCategoryDetailMapper implements Mapper<NewCategoryDetailResponse, NewCategory> {

    private final ModelMapper mapper;

    @Override
    public NewCategoryDetailResponse mapTo(NewCategory b) {
        NewCategoryDetailResponse newCategoryDetailResponse = mapper.map(b, NewCategoryDetailResponse.class);
        newCategoryDetailResponse.setCreatorEmail(b.getCreatedBy().getEmail());
        newCategoryDetailResponse.setCreatorName(b.getCreatedBy().getUsername());
        return newCategoryDetailResponse;
    }

    @Override
    public NewCategory mapFrom(NewCategoryDetailResponse a) {
        return mapper.map(a, NewCategory.class);
    }

}
