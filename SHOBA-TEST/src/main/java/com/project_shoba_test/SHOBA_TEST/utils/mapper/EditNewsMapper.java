package com.project_shoba_test.SHOBA_TEST.utils.mapper;

import java.sql.Timestamp;
import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;
import com.project_shoba_test.SHOBA_TEST.service.AuthService;
import com.project_shoba_test.SHOBA_TEST.service.NewCategoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EditNewsMapper implements Mapper<EditNewsDto, News> {

    private final ModelMapper mapper;

    private final NewCategoryService newCategoryService;

    private final AuthService authService;

    @Override
    public EditNewsDto mapTo(News b) {
        return mapper.map(b, EditNewsDto.class);
    }

    @Override
    public News mapFrom(EditNewsDto a) {
        News news = mapper.map(a, News.class);
        news.setCategory(newCategoryService.getCategoryById(a.getCategoryId()));
        news.setUpdatedAt(Timestamp.from(Instant.now()));
        news.setUpdatedBy(authService.getCurrentUser());
        return news;
    }

}