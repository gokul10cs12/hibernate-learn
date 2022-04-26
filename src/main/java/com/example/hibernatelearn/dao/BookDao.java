package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Book;

import java.util.List;

public interface BookDao {

    List<Book> findAllBooks(int pageSize, int offset);

    List<Book> findAllBook();
    Book getById(Long id);

    Book findBookByTitle(String title);

    Book saveNewBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

}
