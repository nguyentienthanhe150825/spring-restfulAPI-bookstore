package vn.bookstoreProject.bookstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.bookstoreProject.bookstore.service.AuthorService;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {
    // Dependency Injection (constructor)
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }
}
