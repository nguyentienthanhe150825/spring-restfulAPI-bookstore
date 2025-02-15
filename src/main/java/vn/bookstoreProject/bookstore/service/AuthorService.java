package vn.bookstoreProject.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import vn.bookstoreProject.bookstore.domain.Author;
import vn.bookstoreProject.bookstore.domain.Book;
import vn.bookstoreProject.bookstore.domain.response.Meta;
import vn.bookstoreProject.bookstore.domain.response.ResultPaginationDTO;
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

    public ResultPaginationDTO getAllAuthors(Specification<Author> spec, Pageable pageable) {
        Page<Author> pageAuthor = this.authorRepository.findAll(spec, pageable);

        ResultPaginationDTO result = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(pageAuthor.getTotalPages());
        meta.setTotal(pageAuthor.getTotalElements());

        result.setMeta(meta);
        result.setResult(pageAuthor.getContent());

        return result;
    }
}
