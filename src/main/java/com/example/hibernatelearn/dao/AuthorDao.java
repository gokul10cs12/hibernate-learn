package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author findAuthorByNameNative(String firstName, String lastName);

    Author findAuthorByNameCriteria(String firstName, String lastName);

    Author findByFirstName(String firstName);

    List<Author> findAll();
    List<Author> listAuthorByLastNameLik(String lastName);

    Author getById(Long id);
    Author getAuthorByName(String firstName, String lastName);
    Author saveNewAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthorById(Long id);
}

