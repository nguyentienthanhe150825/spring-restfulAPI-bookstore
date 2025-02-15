package vn.bookstoreProject.bookstore.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.bookstoreProject.bookstore.domain.Book;
import vn.bookstoreProject.bookstore.domain.response.ResultPaginationDTO;
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
            throw new InvalidException("Book with title: " + bookRequest.getTitle() + " is exist in database");
        }

        Book newBook = this.bookService.handleCreateBook(bookRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }

    @GetMapping("/books/{id}")
    @ApiMessage("Fetch book by id")
    public ResponseEntity<Book> getBookById(@PathVariable("id") long id) throws InvalidException {
        Book book = this.bookService.getBookById(id);
        if (book == null) {
            throw new InvalidException("Book with id = " + id + " not exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PutMapping("/books")
    @ApiMessage("Update book")
    public ResponseEntity<Book> upateBook(@Valid @RequestBody Book bookRequest) throws InvalidException {
        // check id exist
        Book currentBook = this.bookService.getBookById(bookRequest.getId());
        if (currentBook == null) {
            throw new InvalidException("Book with id = " + bookRequest.getId() + " not exist");
        }

        // If the Book Title found in the database based on the request id is different
        // from the Book Title from the request
        if (!currentBook.getTitle().equals(bookRequest.getTitle())) {
            // // Check if the Title from Request exists in the database or not
            boolean isTitleExist = this.bookService.isTitleExist(bookRequest.getTitle());
            if (isTitleExist) {
                throw new InvalidException("Book with title: " + bookRequest.getTitle() + " is exist in database");
            }
        }

        // update book
        Book bookUpdate = this.bookService.handleUpdateBook(bookRequest, currentBook);

        return ResponseEntity.status(HttpStatus.OK).body(bookUpdate);
    }

    @DeleteMapping("/books/{id}")
    @ApiMessage("Delete a book")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) throws InvalidException {
        Book currentBook = this.bookService.getBookById(id);
        if (currentBook == null) {
            throw new InvalidException("Book with id = " + id + " not exist");
        }

        // Delete book in database
        this.bookService.handleDeleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/books")
    @ApiMessage("fetch all books")
    public ResponseEntity<ResultPaginationDTO> getAllBooks(@Filter Specification<Book> spec, Pageable pageable) {
        // https://github.com/turkraft/springfilter
        ResultPaginationDTO listBooks = this.bookService.getAllBooks(spec, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(listBooks);
    }

}
