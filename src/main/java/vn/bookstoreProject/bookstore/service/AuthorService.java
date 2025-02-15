package vn.bookstoreProject.bookstore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import vn.bookstoreProject.bookstore.domain.Author;
import vn.bookstoreProject.bookstore.repository.AuthorRepository;
import vn.bookstoreProject.bookstore.repository.BookRepository;

@Service
public class AuthorService {
    // Dependency Injection (constructor)
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public boolean isAuthorNameExist(String name) {
        return authorRepository.existsByName(name);
    }

    public Author handleCreateAuthor(Author authorRequest) {
        return this.authorRepository.save(authorRequest);
    }

    public Author getAuthorById(long id) {
        Optional<Author> authorOptional = this.authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            return authorOptional.get();
        }
        return null;
    }

    public Author handleUpdateAuthor(Author authorRequest, Author currentAuthor) {
        // set value
        currentAuthor.setName(authorRequest.getName());
        currentAuthor.setNationality(authorRequest.getNationality());

        // update author in database
        currentAuthor = this.authorRepository.save(currentAuthor);

        return currentAuthor;
    }
}
