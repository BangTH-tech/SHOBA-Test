package com.project_shoba_test.SHOBA_TEST.service.news;

import org.springframework.data.domain.Page;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.AddNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.EditNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.FilterNewListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.news.NewDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.news.NewListResponse;

public interface NewService {
    public Page<NewListResponse> getAllNews(FilterNewListDto filterNewListDto);

    public void addNews(AddNewsDto addNewsDto);

    public void editNews(EditNewsDto editNewsDto);

    public void deleteNews(Long id);

    public NewDetailResponse getNewDetailResponse(Long id);
}
