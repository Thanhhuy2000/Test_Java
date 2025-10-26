package com.example.Buoi5;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Integer id, Book bookDetails) {
        return bookRepository.findById(id).map(book -> {
            book.setName(bookDetails.getName());
            book.setPrice(bookDetails.getPrice());
            book.setQuantity(bookDetails.getQuantity());
            book.setDescription(bookDetails.getDescription());
            book.setAuthor(bookDetails.getAuthor());
            book.setYear(bookDetails.getYear());
            book.setCategories(bookDetails.getCategories());
            return bookRepository.save(book);
        }).orElse(null);
    }

    public void deleteBook(Integer id) {
        bookRepository.deleteById(id);
    }

    // Thống kê số lượng sách theo giá
    public List<Object[]> countBooksByPrice() {
        return bookRepository.countBooksByPrice();
    }

    // Thống kê số lượng sách theo số lượng tồn kho
    public List<Object[]> countBooksByQuantity() {
        return bookRepository.countBooksByQuantity();
    }

    // Tìm kiếm sách theo giá
    public List<Book> findByPrice(Double price) {
        return bookRepository.findByPrice(price);
    }
    public List<Book> findByPriceBetween(Double min, Double max) {
        return bookRepository.findByPriceBetween(min, max);
    }

    // Tìm kiếm sách theo tên và giá
    public List<Book> findByNameAndPrice(String name, Double min, Double max) {
        return bookRepository.findByNameContainingIgnoreCaseAndPriceBetween(name, min, max);
    }
}
