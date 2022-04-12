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
        return jdbcTemplate.queryForObject(
                "select * from author where first_name=? and last_name=?",
                getRawMapper(),
                firstName,
                lastName
        );
    }

    @Override
    public Author saveNewAuthor(Author author) {
        jdbcTemplate.update(
                "insert into author (first_name, last_name) value (?,?)",
                author.getFirstName(),
                author.getLastName()
        );

        Long newUserId = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
        return this.getById(newUserId);
    }

    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update(
                "update author set first_name=?, last_name=? where id=?",
                author.getFirstName(),
                author.getLastName(),
                author.getId()
                );
        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update("delete from author where id= ?", id);

    }

    private RowMapper<Author> getRawMapper(){
        return new AuthorMapper();
    }
}
