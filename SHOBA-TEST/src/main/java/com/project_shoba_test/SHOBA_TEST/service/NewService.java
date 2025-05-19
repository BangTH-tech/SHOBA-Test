package com.project_shoba_test.SHOBA_TEST.service;

import org.springframework.data.domain.Page;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.FilterNewListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewListResponse;

public interface NewService {
    public Page<NewListResponse> getAllNews(FilterNewListDto filterNewListDto);

    public void addNews(AddNewsDto addNewsDto);

    public void editNews(EditNewsDto editNewsDto);

    public void deleteNews(Long id);
}
