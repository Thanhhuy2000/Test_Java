package com.example.Buoi5;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c.id, COUNT(b.id) FROM Category c LEFT JOIN c.books b GROUP BY c.id")
    List<Object[]> countBooksPerCategory();
} 