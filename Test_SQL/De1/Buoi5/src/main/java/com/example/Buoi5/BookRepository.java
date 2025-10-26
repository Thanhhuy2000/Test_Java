package com.example.Buoi5;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {
    // Thống kê số lượng sách theo giá
    @Query("SELECT b.price, COUNT(b) FROM Book b GROUP BY b.price")
    List<Object[]> countBooksByPrice();

    // Thống kê số lượng sách theo số lượng tồn kho
    @Query("SELECT b.quantity, COUNT(b) FROM Book b GROUP BY b.quantity")
    List<Object[]> countBooksByQuantity();

    // Tìm kiếm sách theo giá
    List<Book> findByPrice(Double price);
    List<Book> findByPriceBetween(Double min, Double max);

    // Tìm kiếm sách theo tên và giá
    List<Book> findByNameContainingIgnoreCaseAndPriceBetween(String name, Double min, Double max);
}
