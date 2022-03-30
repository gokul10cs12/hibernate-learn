package com.example.hibernatelearn;

import com.example.hibernatelearn.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest // This load the full context
class HibernateLearnApplicationTests {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testBookRepository(){
        long count = bookRepository.count();
        assertThat(count).isGreaterThan(1);
    }


    @Test
    void contextLoads() {
    }

}
