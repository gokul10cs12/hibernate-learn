package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoImpl implements AuthorDao {

     private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Author getById(Long id) {
        return jdbcTemplate.queryForObject("select * from author where id = ?", getRawMapper(), id);
    }

    @Override
    public Author getAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    private RowMapper<Author> getRawMapper(){
        return new AuthorMapper();
    }
}
