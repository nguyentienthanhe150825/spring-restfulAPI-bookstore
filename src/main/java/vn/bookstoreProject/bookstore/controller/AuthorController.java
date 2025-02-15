package vn.bookstoreProject.bookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.bookstoreProject.bookstore.domain.Author;
import vn.bookstoreProject.bookstore.service.AuthorService;
import vn.bookstoreProject.bookstore.util.annotation.ApiMessage;
import vn.bookstoreProject.bookstore.util.error.InvalidException;

@RestController
@RequestMapping("/api/v1")
public class AuthorController {
    // Dependency Injection (constructor)
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping("/authors")
    @ApiMessage("Create a new author")
    public ResponseEntity<Author> createNewAuthor(@Valid @RequestBody Author authorRequest) throws InvalidException {
        // Check if the authorName from Request exists in the database or not
        boolean isAuthorNameExist = this.authorService.isAuthorNameExist(authorRequest.getName());
        if (isAuthorNameExist) {
            throw new InvalidException("Name " + authorRequest.getName() + " is exist in database");
        }

        Author newAuthor = this.authorService.handleCreateAuthor(authorRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(newAuthor);
    }

    @GetMapping("/authors/{id}")
    @ApiMessage("Fetch author by id")
    public ResponseEntity<Author> getAuthorById(@PathVariable("id") long id) throws InvalidException {
        Author author = this.authorService.getAuthorById(id);
        if (author == null) {
            throw new InvalidException("Author with id = " + id + " not exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(author);
    }

    @PutMapping("/authors")
    @ApiMessage("Update author")
    public ResponseEntity<Author> updateAuthor(@Valid @RequestBody Author authorRequest) throws InvalidException {
        // check id exist
        Author currentAuthor = this.authorService.getAuthorById(authorRequest.getId());
        if (currentAuthor == null) {
            throw new InvalidException("Author with id = " + authorRequest.getId() + " not exist");
        }

        // If the Author Name found in the database based on the request Id is different from the Author Name from the request
        if (!currentAuthor.getName().equals(authorRequest.getName())) {
            // Check if the authorName from Request exists in the database or not
            boolean isAuthorNameExist = this.authorService.isAuthorNameExist(authorRequest.getName());
            if (isAuthorNameExist) {
                throw new InvalidException("Name " + authorRequest.getName() + " is exist in database");
            }
        }

        // update author
        Author authorUpdate = this.authorService.handleUpdateAuthor(authorRequest, currentAuthor);

        return ResponseEntity.status(HttpStatus.OK).body(authorUpdate);
    }

}
