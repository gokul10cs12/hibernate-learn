package com.example.hibernatelearn.repositories;

import com.example.hibernatelearn.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
