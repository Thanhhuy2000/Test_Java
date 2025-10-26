package com.example.Buoi5;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
@Validated
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @Valid @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Thống kê số lượng sách theo giá
    @GetMapping("/statistics/price")
    public List<Object[]> countBooksByPrice() {
        return bookService.countBooksByPrice();
    }

    // Thống kê số lượng sách theo số lượng tồn kho
    @GetMapping("/statistics/quantity")
    public List<Object[]> countBooksByQuantity() {
        return bookService.countBooksByQuantity();
    }

    // Tìm kiếm sách theo giá (hoặc khoảng giá)
    @GetMapping("/search/price")
    public List<Book> searchByPrice(
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max) {
        if (price != null) {
            return bookService.findByPrice(price);
        } else if (min != null && max != null) {
            return bookService.findByPriceBetween(min, max);
        } else {
            return bookService.getAllBooks();
        }
    }

    // Tìm kiếm sách theo tên và khoảng giá
    @GetMapping("/search/name-price")
    public List<Book> searchByNameAndPrice(
            @RequestParam String name,
            @RequestParam Double min,
            @RequestParam Double max) {
        return bookService.findByNameAndPrice(name, min, max);
    }
}
