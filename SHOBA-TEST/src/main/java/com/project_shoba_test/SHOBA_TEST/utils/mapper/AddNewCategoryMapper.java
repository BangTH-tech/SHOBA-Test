package com.project_shoba_test.SHOBA_TEST.utils.mapper;

import java.sql.Timestamp;
import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.service.AuthService;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddNewCategoryMapper implements Mapper<AddNewCategoryDto, NewCategory> {

    private final ModelMapper mapper;

    private final AuthService authService;

    @Override
    public AddNewCategoryDto mapTo(NewCategory b) {
        return mapper.map(b, AddNewCategoryDto.class);
    }

    @Override
    public NewCategory mapFrom(AddNewCategoryDto a) {
        NewCategory news = mapper.map(a, NewCategory.class);
        news.setCreatedAt(Timestamp.from(Instant.now()));
        news.setCreatedBy(authService.getCurrentUser());
        return news;
    }

}