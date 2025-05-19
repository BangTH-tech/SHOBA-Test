package com.project_shoba_test.SHOBA_TEST.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.exception.BadRequestException;
import com.project_shoba_test.SHOBA_TEST.exception.NotFoundException;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.AddNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.EditNewsDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.FilterNewListDto;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.NewListResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;
import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.repository.NewRepository;
import com.project_shoba_test.SHOBA_TEST.service.NewService;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.AddNewsMapper;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.EditNewsMapper;
import com.project_shoba_test.SHOBA_TEST.utils.mapper.NewsListMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewServiceImpl implements NewService {

    private final NewRepository newRepository;

    private final NewsListMapper newsListMapper;

    private final AddNewsMapper addNewsMapper;

    private final EditNewsMapper editNewsMapper;

    @Override
    public Page<NewListResponse> getAllNews(FilterNewListDto filterNewListDto) {
        Pageable pageable;
        if (filterNewListDto.getSortBy() != null &&
                (filterNewListDto.getSortBy().equals("id") ||
                        filterNewListDto.getSortBy().equals("title") ||
                        filterNewListDto.getSortBy().equals("createdAt"))) {
            Sort sort = filterNewListDto.isAscending() ? Sort.by(filterNewListDto.getSortBy()).ascending()
                    : Sort.by(filterNewListDto.getSortBy()).descending();
            pageable = PageRequest.of(filterNewListDto.getPage(), filterNewListDto.getSize(), sort);
        } else {
            pageable = PageRequest.of(filterNewListDto.getPage(), filterNewListDto.getSize());
        }
        Page<News> newsList = newRepository.getNewsList(
                filterNewListDto.getSearch(),
                filterNewListDto.getCategoryId(),
                pageable);
        return newsList.map(newsListMapper::mapTo);
    }

    @Override
    public void addNews(AddNewsDto addNewsDto) {
        if (newRepository.existsByTitle(addNewsDto.getTitle())) {
            throw new BadRequestException("News with this title already exists", "News with this title already exists");
        }

        News news = addNewsMapper.mapFrom(addNewsDto);
        newRepository.save(news);
    }

    @Override
    public void editNews(EditNewsDto editNewsDto) {
        News oldNews = newRepository.findById(editNewsDto.getId())
                .orElseThrow(() -> new NotFoundException("News not found", "News not found"));
        if (!oldNews.getTitle().equals(editNewsDto.getTitle()) && newRepository.existsByTitle(editNewsDto.getTitle())) {
            throw new BadRequestException("News with this title already exists", "News with this title already exists");
        }

        News newNews = editNewsMapper.mapFrom(editNewsDto);
        newNews.setCreatedAt(oldNews.getCreatedAt());
        newNews.setCreatedBy(oldNews.getCreatedBy());
        newRepository.save(newNews);
    }

    @Override
    public void deleteNews(Long id) {
        News news = newRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("News not found", "News not found"));
        newRepository.delete(news);
    }

}
