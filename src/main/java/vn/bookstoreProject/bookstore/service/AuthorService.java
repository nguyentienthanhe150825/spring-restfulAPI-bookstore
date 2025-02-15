package vn.bookstoreProject.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import vn.bookstoreProject.bookstore.domain.Author;
import vn.bookstoreProject.bookstore.domain.Book;
import vn.bookstoreProject.bookstore.repository.AuthorRepository;
import vn.bookstoreProject.bookstore.repository.BookRepository;

@Service
public class AuthorService {
    // Dependency Injection (constructor)
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
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

    public void handleDeleteAuthor(long id) {
        Author author = this.getAuthorById(id);
        if (author != null) {
            // Fetch all book belong to this author
            List<Book> listBooks = this.bookRepository.findByAuthor(author);

            // Delete all book
            this.bookRepository.deleteAll(listBooks);
        }
        // Delete author by id
        this.authorRepository.deleteById(id);
    }
}
