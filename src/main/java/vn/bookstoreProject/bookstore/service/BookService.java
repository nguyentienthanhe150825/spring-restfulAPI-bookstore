package vn.bookstoreProject.bookstore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

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

    public Book getBookById(long id) {
        Optional<Book> bookOptional = this.bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get();
        }
        return null;
    }

    public Book handleUpdateBook(Book bookRequest, Book currentBook) {
        // set value
        currentBook.setTitle(bookRequest.getTitle());
        currentBook.setPublishedDate(bookRequest.getPublishedDate());
        currentBook.setIsbn(bookRequest.getIsbn());
        currentBook.setPrice(bookRequest.getPrice());

        // check Author: If change author
        if (bookRequest.getAuthor() != null) {
            Author athor = this.authorService.getAuthorById(bookRequest.getAuthor().getId());
            currentBook.setAuthor(athor != null ? athor : null);
        }

        // update book in database
        currentBook = this.bookRepository.save(currentBook);

        return currentBook;
    }

}
