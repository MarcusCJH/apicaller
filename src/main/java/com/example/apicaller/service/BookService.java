package com.example.apicaller.service;

import com.example.apicaller.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private RestTemplate restTemplate;

    private final String CRUD_API_URL = "http://13.229.129.5:8080/api/books"; // Replace with the actual IP of the CRUD app

    public List<Book> getAllBooks() {
        logger.info("Calling CRUD API to fetch all books");
        List<Book> books = Arrays.asList(restTemplate.getForObject(CRUD_API_URL, Book[].class));
        logger.info("Successfully fetched {} books from CRUD API", books.size());
        return books;
    }

    public Book getBookById(Long id) {
        logger.info("Calling CRUD API to fetch book with ID: {}", id);
        Book book = restTemplate.getForObject(CRUD_API_URL + "/" + id, Book.class);
        if (book != null) {
            logger.info("Successfully fetched book with ID {}: {}", id, book.getTitle());
        } else {
            logger.warn("Book with ID {} not found in CRUD API", id);
        }
        return book;
    }

    public Book createBook(Book book) {
        logger.info("Calling CRUD API to create a new book: {}", book.getTitle());
        Book createdBook = restTemplate.postForObject(CRUD_API_URL, book, Book.class);
        logger.info("Book created successfully in CRUD API with ID: {}", createdBook.getId());
        return createdBook;
    }

    public void updateBook(Long id, Book book) {
        logger.info("Calling CRUD API to update book with ID: {}", id);
        restTemplate.put(CRUD_API_URL + "/" + id, book);
        logger.info("Book with ID {} updated successfully in CRUD API", id);
    }

    public void deleteBook(Long id) {
        logger.info("Calling CRUD API to delete book with ID: {}", id);
        restTemplate.delete(CRUD_API_URL + "/" + id);
        logger.info("Book with ID {} deleted successfully from CRUD API", id);
    }
}
