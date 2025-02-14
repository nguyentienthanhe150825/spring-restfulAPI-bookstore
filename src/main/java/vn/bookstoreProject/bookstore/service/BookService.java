package vn.bookstoreProject.bookstore.service;

import org.springframework.stereotype.Service;

import vn.bookstoreProject.bookstore.repository.BookRepository;

@Service
public class BookService {
    // Dependency Injection (constructor)
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
}
