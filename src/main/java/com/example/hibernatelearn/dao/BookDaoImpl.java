package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Book;
import com.example.hibernatelearn.repositories.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    private final BookRepository bookRepository;

    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }

    @Override
    public List<Book> findAllBookSortByTitle(Pageable pageable) {
        return null;
    }

    @Override
    public List<Book> findAllBook(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent(); //get the results.
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        /*
        * check this again.
        * taking the offset value and page size to generate the page number.
        * */
        Pageable pageable= PageRequest.ofSize(pageSize);

        if (offset>0){
            pageable = pageable.withPage(offset / pageSize); //calculating the page number.
        } else {
            pageable = pageable.withPage(0);
        }
        return findAllBook(pageable);
    }

    @Override
    public List<Book> findAllBook() {
        return null;
    }

    @Override
    public Book findByISBN(String isbn) {
        return null;
    }

    @Override
    public Book getById(Long id) {
        return null;
    }

    @Override
    public Book findBookByTitle(String title) {
        return null;
    }

    @Override
    public Book saveNewBook(Book book) {
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public void deleteBookById(Long id) {

    }
}
