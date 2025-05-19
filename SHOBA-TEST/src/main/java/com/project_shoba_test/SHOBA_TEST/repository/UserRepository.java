package com.project_shoba_test.SHOBA_TEST.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project_shoba_test.SHOBA_TEST.model.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Query("SELECT u FROM Users u WHERE u.username = :text OR u.email = :text")
    public Optional<Users> existsByUsernameOrEmail(@Param("text") String text);
}
