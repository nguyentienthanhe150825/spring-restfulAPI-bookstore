package vn.bookstoreProject.bookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.bookstoreProject.bookstore.domain.Book;
import vn.bookstoreProject.bookstore.service.BookService;
import vn.bookstoreProject.bookstore.util.annotation.ApiMessage;
import vn.bookstoreProject.bookstore.util.error.InvalidException;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    // Dependency Injection (constructor)
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    @ApiMessage("Create a new book")
    public ResponseEntity<Book> createNewBook(@Valid @RequestBody Book bookRequest) throws InvalidException {
        // check title exist in database
        boolean isTitleExist = this.bookService.isTitleExist(bookRequest.getTitle());

        if (isTitleExist) {
            throw new InvalidException("Book with title: " + bookRequest.getTitle() + " is exist");
        }

        Book newBook = this.bookService.handleCreateBook(bookRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

}
