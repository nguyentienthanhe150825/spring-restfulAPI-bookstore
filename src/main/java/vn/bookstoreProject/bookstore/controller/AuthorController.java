package vn.bookstoreProject.bookstore.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<String> createNewAuthor(@Valid @RequestBody Author authorRequest) throws InvalidException {
        // check authorName exist in database
        boolean isAuthorNameExist = this.authorService.isAuthorNameExist(authorRequest.getName());
        
        if(isAuthorNameExist) {
            throw new InvalidException("Name " + authorRequest.getName() + " is exist");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("create success");
    }

}
