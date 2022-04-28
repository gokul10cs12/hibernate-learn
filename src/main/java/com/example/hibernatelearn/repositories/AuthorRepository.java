package com.example.hibernatelearn.repositories;

import com.example.hibernatelearn.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    /*
    *
    * The findAll(Pageable pageable) method by default returns a Page object.
    * A Page object provides lots of extra useful information other than just list of employees in current page.
    *  E.g. A Page object has the number of total pages, number of the current page and well as whether current page is first page or last page.
    * */
    Page<Author> findAuthorByLastName(String lastName, Pageable pageable);
    Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);
}
