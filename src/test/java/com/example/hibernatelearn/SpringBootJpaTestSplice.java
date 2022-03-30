package com.example.hibernatelearn;

import com.example.hibernatelearn.domain.Book;
import com.example.hibernatelearn.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class SpringBootJpaTestSplice {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testJpaTestSplice(){
        long countBefore = bookRepository.count();
        bookRepository.save(new Book("Name", "isbn", "hehe"));
        assertThat(countBefore + 1).isEqualTo(bookRepository.count());
    }
}