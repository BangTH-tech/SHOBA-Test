package com.project_shoba_test.SHOBA_TEST.utils.mapper.news;

import java.sql.Timestamp;
import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.AddNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;
import com.project_shoba_test.SHOBA_TEST.service.auth.AuthService;
import com.project_shoba_test.SHOBA_TEST.service.newCategory.NewCategoryService;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.Mapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddNewsMapper implements Mapper<AddNewsDto, News> {

    private final ModelMapper mapper;

    private final NewCategoryService newCategoryService;

    private final AuthService authService;

    @Override
    public AddNewsDto mapTo(News b) {
        return mapper.map(b, AddNewsDto.class);
    }

    @Override
    public News mapFrom(AddNewsDto a) {
        News news = mapper.map(a, News.class);
        news.setCategory(newCategoryService.getCategoryById(a.getCategoryId()));
        news.setCreatedAt(Timestamp.from(Instant.now()));
        news.setCreatedBy(authService.getCurrentUser());
        return news;
    }

}
