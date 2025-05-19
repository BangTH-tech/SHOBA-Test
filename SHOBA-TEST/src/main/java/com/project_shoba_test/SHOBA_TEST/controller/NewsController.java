package com.project_shoba_test.SHOBA_TEST.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project_shoba_test.SHOBA_TEST.model.dto.request.news.FilterNewListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.news.NewDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.news.NewListResponse;
import com.project_shoba_test.SHOBA_TEST.service.news.NewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
public class NewsController {
    private final NewService newService;

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
}
