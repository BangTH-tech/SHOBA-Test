package com.project_shoba_test.SHOBA_TEST.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.FilterEmployeeListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.FilterNewListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.EmployeeListResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewCategoryListResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewListResponse;
import com.project_shoba_test.SHOBA_TEST.service.NewCategoryService;
import com.project_shoba_test.SHOBA_TEST.service.NewService;
import com.project_shoba_test.SHOBA_TEST.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;

    private final NewService newService;

    private final NewCategoryService newCategoryService;
    
    @PostMapping("/employee-list")
    public ResponseEntity<Page<EmployeeListResponse>> getEmployeeList(@RequestBody @Valid FilterEmployeeListDto filterEmployeeListDto) {
        Page<EmployeeListResponse> employeeList = userService.getAllEmployees(filterEmployeeListDto);
        if(employeeList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody @Valid AddUserDto addUserDto) {
        userService.addUser(addUserDto);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/edit-user")
    public ResponseEntity<?> editUser(@RequestBody @Valid EditUserDto editUserDto) {
        userService.editUser(editUserDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/change-status/{userId}")
    public ResponseEntity<?> changeUserStatus(@PathVariable Long userId) {
        userService.changeUserStatus(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/news-list")
    public ResponseEntity<Page<NewListResponse>> getNewsList(@RequestBody @Valid FilterNewListDto filterNewListDto) {
        Page<NewListResponse> newsList = newService.getAllNews(filterNewListDto);
        if(newsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newsList);
    }

    @PostMapping("/add-news")
    public ResponseEntity<?> addNews(@RequestBody @Valid AddNewsDto addNewsDto) {
        newService.addNews(addNewsDto);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/edit-news")
    public ResponseEntity<?> editNews(@RequestBody @Valid EditNewsDto editNewsDto) {
        newService.editNews(editNewsDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-news/{newsId}")
    public ResponseEntity<?> deleteNews(@PathVariable Long newsId) {
        newService.deleteNews(newsId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/new-category-list")
    public ResponseEntity<List<NewCategoryListResponse>> getNewCategoryList() {
        List<NewCategoryListResponse> newCategoryList = newCategoryService.getAllNewCategory();
        if(newCategoryList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newCategoryList);
    }

    @PostMapping("/add-new-category")
    public ResponseEntity<?> addNewCategory(@RequestBody @Valid AddNewCategoryDto addNewCategoryDto) {
        newCategoryService.addNewCategory(addNewCategoryDto);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/edit-new-category")
    public ResponseEntity<?> editNewCategory(@RequestBody @Valid EditNewCategoryDto editNewCategoryDto) {
        newCategoryService.editNewCategory(editNewCategoryDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-new-category/{categoryId}")
    public ResponseEntity<?> deleteNewCategory(@PathVariable Long categoryId) {
        newCategoryService.deleteNewCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
