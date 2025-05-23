package com.project_shoba_test.SHOBA_TEST.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;

public interface NewRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n WHERE (cast(:search as text) IS NULL OR " +
                "(LOWER(n.title) LIKE '%' || LOWER(cast(:search as text)) || '%')) AND (:category IS NULL OR n.category.id = :category)")
    public Page<News> getNewsList(
        @Param("search") String search,
        @Param("category") Long categoryId,
        Pageable pageable
    );

    public boolean existsByTitle(String title);

    public boolean existsByCategory(NewCategory newCategory);
}
