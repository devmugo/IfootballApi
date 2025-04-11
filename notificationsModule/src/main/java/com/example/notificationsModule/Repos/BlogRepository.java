package com.example.notificationsModule.Repos;

import com.example.notificationsModule.Models.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    // ✅ Search by title OR tags (case-insensitive)
    @Query("SELECT b FROM Blog b LEFT JOIN b.tags t WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(t) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Blog> searchByTitleOrTags(@Param("keyword") String keyword, Pageable pageable);
}

