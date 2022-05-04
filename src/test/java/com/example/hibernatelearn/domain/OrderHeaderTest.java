package com.example.hibernatelearn.domain;

import com.example.hibernatelearn.repositories.OrderHeaderRepository;
import com.example.hibernatelearn.repositories.ProductRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"package com.example.hibernatelearn.dao"} )
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    Product product;


    @Test
    void testGetCategory(){
        Product product1 = productRepository.findProductByDescription("PRODUCT1");

        assertNotNull(product1);
        assertNotNull(product1.getCategories());
    }

    @BeforeEach
    void setUp(){
        Product product1 = new Product();
        product1.setProductStatus(ProductStatus.NEW);
        product1.setDescription("test Product");
        product = productRepository.saveAndFlush(product1);
    }


    @Test
    void testSavedOrderWithLine(){
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer("new Customer");


        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

//        orderHeader.setOrderLines(Set.of(orderLine));
//        orderLine.setOrderHeader(orderHeader);  Same approach achieved using associate helper addOrderLine()
        orderHeader.addOrderLine(orderLine);
        OrderHeader saveOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(saveOrder);
        assertNotNull(saveOrder.getId());
        assertNotNull(saveOrder.getOrderLines());
        assertEquals(saveOrder.getOrderLines().size(),1);

    }

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