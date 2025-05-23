package com.project_shoba_test.SHOBA_TEST.utils.mapper.newCategory;

import java.sql.Timestamp;
import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.newCategory.AddNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.newCategory.EditNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.service.auth.AuthService;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EditNewCategoryMapper implements Mapper<EditNewCategoryDto, NewCategory> {

    private final ModelMapper mapper;

    private final AuthService authService;

    @Override
    public EditNewCategoryDto mapTo(NewCategory b) {
        return mapper.map(b, EditNewCategoryDto.class);
    }

    @Override
    public NewCategory mapFrom(EditNewCategoryDto a) {
        NewCategory news = mapper.map(a, NewCategory.class);
        news.setUpdatedAt(Timestamp.from(Instant.now()));
        news.setUpdatedBy(authService.getCurrentUser());
        return news;
    }

}