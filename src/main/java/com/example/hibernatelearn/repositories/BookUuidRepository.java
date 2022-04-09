package com.example.hibernatelearn.repositories;

import com.example.hibernatelearn.domain.BookUuid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
public interface BookUuidRepository extends JpaRepository<BookUuid, UUID> {
}
