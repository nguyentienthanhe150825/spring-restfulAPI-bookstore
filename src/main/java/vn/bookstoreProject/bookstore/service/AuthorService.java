package vn.bookstoreProject.bookstore.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import vn.bookstoreProject.bookstore.domain.Author;
import vn.bookstoreProject.bookstore.repository.AuthorRepository;

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
        if(authorOptional.isPresent()) {
            return authorOptional.get();
        }
        return null;
    }
}
