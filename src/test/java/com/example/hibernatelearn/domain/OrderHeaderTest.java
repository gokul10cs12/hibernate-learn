package com.example.hibernatelearn.domain;

import com.example.hibernatelearn.repositories.OrderHeaderRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"package com.example.hibernatelearn.dao"} )
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Test
    void testSaveOrder() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("new Customer");
        OrderHeader saveOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(saveOrder);
        assertNotNull(saveOrder.getId());

        OrderHeader fetchOrder = orderHeaderRepository.getById(saveOrder.getId());

        assertNotNull(fetchOrder);
        assertNotNull(fetchOrder.getId());
        assertNotNull(fetchOrder.getCreatedDate());
        assertNotNull(fetchOrder.getLastModifiedDate());
    }

}