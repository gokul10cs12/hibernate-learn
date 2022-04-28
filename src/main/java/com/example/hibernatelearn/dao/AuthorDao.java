package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorDao {

    List<Author> findAllAuthorByLastName(String lastName, Pageable pageable);

    Author getById(Long id);
    Author getAuthorByName(String firstName, String lastName);
    Author saveNewAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthorById(Long id);
}

