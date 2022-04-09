package com.example.hibernatelearn.repositories;

import com.example.hibernatelearn.domain.composite.AuthorEmbedded;
import com.example.hibernatelearn.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
