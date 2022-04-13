package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    //revisit the left outer join implementation


    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject("select * from book where id= ?", getBookRowMapper(), id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return jdbcTemplate.queryForObject("select * from book where title= ?", getBookRowMapper(), title);
    }

    @Override
    public Book saveNewBook(Book book) {
        jdbcTemplate.update(
                "insert into book(isbn, publisher, title, author_id) value(?,?,?,?)",
                book.getIsbn(),
                book.getPublisher(),
                book.getTitle(),
                book.getAuthorId()
        );

        Long newBookId = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
        return this.getById(newBookId);
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update(
                "update book set publisher=?, title=? where id=?",
                book.getPublisher(),
                book.getTitle(),
                book.getId()
        );

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("delete from book where id= ?", id);
    }

    private RowMapper<Book> getBookRowMapper(){
        return new BookMapper();
    }
}
