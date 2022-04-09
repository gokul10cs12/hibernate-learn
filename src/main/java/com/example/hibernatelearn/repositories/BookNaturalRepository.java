package com.example.hibernatelearn.repositories;

import com.example.hibernatelearn.domain.BookNatural;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
