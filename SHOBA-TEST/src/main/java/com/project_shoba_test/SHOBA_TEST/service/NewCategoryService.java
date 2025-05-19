package com.project_shoba_test.SHOBA_TEST.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.FilterNewListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewCategoryListResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;

public interface NewCategoryService {
    public NewCategory getCategoryById(Long id);

    public List<NewCategoryListResponse> getAllNewCategory();

    public void addNewCategory(AddNewCategoryDto addNewCategoryDto);

    public void editNewCategory(EditNewCategoryDto editNewCategoryDto);

    public void deleteNewCategory(Long id);
}
