package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao {

    List<Book> findAllBooksSortByTitle(Pageable pageable);
    List<Book> findAllBooks(Pageable pageable);
    List<Book> findAllBooks(int pageSize, int offset);
    List<Book> findAllBooks();

    Book findByISBN(String isbn);
    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);
}
