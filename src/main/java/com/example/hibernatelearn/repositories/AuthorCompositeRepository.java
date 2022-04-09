package com.example.hibernatelearn.repositories;

import com.example.hibernatelearn.domain.composite.AuthorComposite;
import com.example.hibernatelearn.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorCompositeRepository extends JpaRepository<AuthorComposite, NameId> {
}
