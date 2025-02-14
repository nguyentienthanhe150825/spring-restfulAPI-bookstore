package vn.bookstoreProject.bookstore.service;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import vn.bookstoreProject.bookstore.domain.Author;
import vn.bookstoreProject.bookstore.domain.Book;
import vn.bookstoreProject.bookstore.repository.BookRepository;

@Service
public class BookService {
    // Dependency Injection (constructor)
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public boolean isTitleExist(String title) {
        return bookRepository.existsByTitle(title);
    }

    public Book handleCreateBook(Book book) {
        // check Author is exist
        if (book.getAuthor() != null) {
            Author author = this.authorService.getAuthorById(book.getAuthor().getId());
            if (author != null) {
                book.setAuthor(author);
            } else {
                book.setAuthor(null);
            }
        }
        return this.bookRepository.save(book);
    }
    
}
