package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao {

    /*
     *
     * The findAll(Pageable pageable) method by default returns a Page object.
     * A Page object provides lots of extra useful information other than just list of employees in current page.
     *  E.g. A Page object has the number of total pages, number of the current page and well as whether current page is first page or last page.
     * */

    List<Book> findAllBookSortByTitle(Pageable pageable);

    List<Book> findAllBook(Pageable pageable);

    List<Book> findAllBooks(int pageSize, int offset);

    List<Book> findAllBook();

    Book findByISBN(String isbn);
    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);
}
