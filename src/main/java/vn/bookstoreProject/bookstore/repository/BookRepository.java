package vn.bookstoreProject.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.bookstoreProject.bookstore.domain.Author;
import vn.bookstoreProject.bookstore.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book>{
    
    boolean existsByTitle(String title);

    List<Book> findByAuthor(Author author);
}
