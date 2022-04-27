package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"package com.example.hibernatelearn.dao"} )
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class BookDaoImplTest {

    @Autowired
    BookDao bookDao;

    @Test
    void testFindBooksSortByTitle(){
        List<Book> books = bookDao.findAllBook(PageRequest.of(0, 7,
                Sort.by(Sort.Order.desc("title"))));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(7);
    }


    @Test
    void testFindBooksByTitle(){
        List<Book> books = bookDao.findAllBook(PageRequest.of(0, 2));
        assertThat(books.size()).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }


}