package com.project_shoba_test.SHOBA_TEST.service.newCategory;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.newCategory.AddNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.newCategory.EditNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.AddNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.EditNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.FilterNewListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.NewCategoryDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.NewCategoryListResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.news.NewListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;

public interface NewCategoryService {
    public NewCategory getCategoryById(Long id);

    public NewCategoryDetailResponse getCategoryDetailResponseById(Long id);

    public List<NewCategoryListResponse> getAllNewCategory();

    public void addNewCategory(AddNewCategoryDto addNewCategoryDto);

    public void editNewCategory(EditNewCategoryDto editNewCategoryDto);

    public void deleteNewCategory(Long id);
}
