package vn.bookstoreProject.bookstore.service;

import vn.bookstoreProject.bookstore.repository.AuthorRepository;

public class AuthorService {
    // Dependency Injection (constructor)
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
}
