package com.project_shoba_test.SHOBA_TEST.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project_shoba_test.SHOBA_TEST.model.entity.NewCategory;
import com.project_shoba_test.SHOBA_TEST.model.entity.News;

public interface NewCategoryRepository extends JpaRepository<NewCategory, Long> {
    public boolean existsByName(String name);
}
