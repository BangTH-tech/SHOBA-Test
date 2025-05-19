package com.project_shoba_test.SHOBA_TEST.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.exception.BadRequestException;
import com.project_shoba_test.SHOBA_TEST.exception.NotFoundException;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewCategoryListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;
import com.project_shoba_test.SHOBA_TEST.repository.NewCategoryRepository;
import com.project_shoba_test.SHOBA_TEST.repository.NewRepository;
import com.project_shoba_test.SHOBA_TEST.service.NewCategoryService;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.AddNewCategoryMapper;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.EditNewCategoryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewCategoryServiceImpl implements NewCategoryService {

    private final NewCategoryRepository newCategoryRepository;

    private final AddNewCategoryMapper addNewCategoryMapper;

    private final EditNewCategoryMapper editNewCategoryMapper;

    private final NewRepository newRepository;

    @Override
    public NewCategory getCategoryById(Long id) {
        return newCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found", "Category not found"));
    }

    @Override
    public List<NewCategoryListResponse> getAllNewCategory() {
        List<NewCategory> categories = newCategoryRepository.findAll();
        return categories.stream()
                .map(category -> new NewCategoryListResponse(category.getId(), category.getName(), category.getCreatedBy().getUsername(), category.getCreatedBy().getEmail()))
                .toList();
    }

    @Override
    public void addNewCategory(AddNewCategoryDto addNewCategoryDto) {
        if(newCategoryRepository.existsByName(addNewCategoryDto.getName())){
            throw new BadRequestException("Category with this title already exists", "Category with this title already exists");
        }
        
        NewCategory newCategory = addNewCategoryMapper.mapFrom(addNewCategoryDto);
        newCategoryRepository.save(newCategory);
    }

    @Override
    public void editNewCategory(EditNewCategoryDto editNewCategoryDto) {
        NewCategory oldCategory = newCategoryRepository.findById(editNewCategoryDto.getId())
                .orElseThrow(() -> new NotFoundException("Category not found", "Category not found"));
        if(!oldCategory.getName().equals(editNewCategoryDto.getName()) && newCategoryRepository.existsByName(editNewCategoryDto.getName())){
            throw new BadRequestException("Category with this title already exists", "Category with this title already exists");
        }
        
        NewCategory newCategory = editNewCategoryMapper.mapFrom(editNewCategoryDto);
        newCategory.setCreatedAt(oldCategory.getCreatedAt());
        newCategory.setCreatedBy(oldCategory.getCreatedBy());
        newCategoryRepository.save(newCategory);
    }

    @Override
    public void deleteNewCategory(Long id) {
        NewCategory newCategory = getCategoryById(id);
        if(newRepository.existsByCategory(newCategory)) {
            throw new BadRequestException("This category belongs to some news. Please delete them first", "This category belongs to some news. Please delete them first");
        }
    }
    
}
