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

import com.project_shoba_test.SHOBA_TEST.model.dto.request.log.FilterLogListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.newCategory.AddNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.newCategory.EditNewCategoryDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.AddNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.EditNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.FilterNewListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.AddUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.EditUserDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.user.FilterEmployeeListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.log.HttpLogResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.CategoryShortResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.NewCategoryDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.newCategory.NewCategoryListResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.news.NewDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.news.NewListResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.user.EmployeeDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.user.EmployeeListResponse;
import com.project_shoba_test.SHOBA_TEST.service.log.HttpLogService;
import com.project_shoba_test.SHOBA_TEST.service.newCategory.NewCategoryService;
import com.project_shoba_test.SHOBA_TEST.service.news.NewService;
import com.project_shoba_test.SHOBA_TEST.service.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final UserService userService;

    private final NewService newService;

    private final NewCategoryService newCategoryService;

    private final HttpLogService httpLogService;

    @GetMapping("/category-short-response")
    public ResponseEntity<List<CategoryShortResponse>> getAllCategoryShortResponse() {
        return ResponseEntity.ok(newCategoryService.getAllCategoryShortResponse());
    }
    

    @PostMapping("/employee-list")
    public ResponseEntity<Page<EmployeeListResponse>> getEmployeeList(
            @RequestBody @Valid FilterEmployeeListDto filterEmployeeListDto) {
        Page<EmployeeListResponse> employeeList = userService.getAllEmployees(filterEmployeeListDto);
        if (employeeList.isEmpty()) {
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

    @PutMapping("/change-status/{userId}")
    public ResponseEntity<?> changeUserStatus(@PathVariable Long userId) {
        userService.changeUserStatus(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/news-list")
    public ResponseEntity<Page<NewListResponse>> getNewsList(@RequestBody @Valid FilterNewListDto filterNewListDto) {
        Page<NewListResponse> newsList = newService.getAllNews(filterNewListDto);
        if (newsList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newsList);
    }

    @GetMapping("/news-detail/{newsId}")
    public ResponseEntity<NewDetailResponse> getNewsDetail(@PathVariable Long newsId) {
        return ResponseEntity.ok(newService.getNewDetailResponse(newsId));
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
        if (newCategoryList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newCategoryList);
    }

    @GetMapping("/new-category-detail/{categoryId}")
    public ResponseEntity<NewCategoryDetailResponse> getNewCategoryDetail(@PathVariable Long categoryId) {
        return ResponseEntity.ok(newCategoryService.getCategoryDetailResponseById(categoryId));
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

    @GetMapping("/employee-detail/{id}")
    public ResponseEntity<EmployeeDetailResponse> getEmployeeDetail(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getEmployeeDetailResponse(id));
    }
    
    @PostMapping("/log-list")
    public ResponseEntity<Page<HttpLogResponse>> getLogList(@RequestBody @Valid FilterLogListDto filterLogListDto) {
        Page<HttpLogResponse> logList = httpLogService.getAllLog(filterLogListDto);
        if (logList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(logList);
    }
}
