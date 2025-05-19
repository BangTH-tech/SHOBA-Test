package com.project_shoba_test.SHOBA_TEST.service.impl;

import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.exception.NotFoundException;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.repository.NewCategoryRepository;
import com.project_shoba_test.SHOBA_TEST.service.NewCategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewCategoryServiceImpl implements NewCategoryService {

    private final NewCategoryRepository newCategoryRepository;

    @Override
    public NewCategory getCategoryById(Long id) {
        return newCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found", "Category not found"));
    }
    
}
