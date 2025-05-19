package com.project_shoba_test.SHOBA_TEST.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project_shoba_test.SHOBA_TEST.model.entity.Users;
import com.project_shoba_test.SHOBA_TEST.model.enums.UserRole;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u WHERE u.username = :text OR u.email = :text")
    public Optional<Users> existsByUsernameOrEmail(@Param("text") String text);

    @Query("SELECT u FROM Users u WHERE (cast(:search as text) IS NULL OR " +
                "((LOWER(u.username) LIKE '%' || LOWER(cast(:search as text)) || '%') OR " +
                "(LOWER(u.email) LIKE '%' || LOWER(cast(:search as text)) || '%') OR " +
                "(LOWER(u.fullName) LIKE '%' || LOWER(cast(:search as text)) || '%'))) " +
                "AND (COALESCE(:role, u.role) = u.role) ")
    public Page<Users> getEmployeeList(
        @Param("search") String search,
        @Param("role") UserRole role,
        Pageable pageable
    );
}
