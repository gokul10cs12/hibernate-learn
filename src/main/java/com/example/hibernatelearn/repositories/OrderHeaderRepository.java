package com.example.hibernatelearn.repositories;

import com.example.hibernatelearn.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
