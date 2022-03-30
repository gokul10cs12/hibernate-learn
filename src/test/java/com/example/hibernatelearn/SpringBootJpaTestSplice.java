package com.example.hibernatelearn;

import com.example.hibernatelearn.domain.Book;
import com.example.hibernatelearn.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //initialize the order
@DataJpaTest //test splice only to test the db layer of the project, very minimal configuration
@ComponentScan(basePackages = {"com.example.hibernatelearn.bootstrap"}) //add the componnent to the context
public class SpringBootJpaTestSplice {

    @Autowired
    BookRepository bookRepository;

    @Commit //make the transaction persist after execution of the test
    @Order(1) //test order
    @Test
    void testJpaTestSplice(){
        long countBefore = bookRepository.count();
        bookRepository.save(new Book("Name", "isbn", "hehe"));
        assertThat(countBefore + 1).isEqualTo(3);
    }

    @Rollback(value = false) // not  rollback, make the transaction persist after execution of the test
    @Order(2)
    @Test
    void testJpaTestSpliceTransaction(){
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(3);
    }
}
