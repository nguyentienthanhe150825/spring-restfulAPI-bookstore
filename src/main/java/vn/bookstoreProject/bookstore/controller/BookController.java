package vn.bookstoreProject.bookstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.bookstoreProject.bookstore.service.BookService;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    // Dependency Injection (constructor)
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
}
