package com.example.apicaller.controller;

import com.example.apicaller.model.Book;
import com.example.apicaller.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        logger.info("Received request to fetch all books");
        List<Book> books = bookService.getAllBooks();
        logger.info("Fetched {} books", books.size());
        return books;
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        logger.info("Received request to fetch book with ID: {}", id);
        Book book = bookService.getBookById(id);
        if (book != null) {
            logger.info("Book found: {}", book.getTitle());
        } else {
            logger.warn("Book with ID {} not found", id);
        }
        return book;
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        logger.info("Received request to create a new book: {}", book.getTitle());
        Book createdBook = bookService.createBook(book);
        logger.info("Book created successfully with ID: {}", createdBook.getId());
        return createdBook;
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody Book book) {
        logger.info("Received request to update book with ID: {}", id);
        bookService.updateBook(id, book);
        logger.info("Book with ID {} updated successfully", id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        logger.info("Received request to delete book with ID: {}", id);
        bookService.deleteBook(id);
        logger.info("Book with ID {} deleted successfully", id);
    }
}
