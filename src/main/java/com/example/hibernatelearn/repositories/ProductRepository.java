package com.example.hibernatelearn.repositories;

import com.example.hibernatelearn.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
