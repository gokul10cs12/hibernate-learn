package com.example.hibernatelearn;

import com.example.hibernatelearn.dao.AuthorDao;
import com.example.hibernatelearn.dao.BookDao;
import com.example.hibernatelearn.domain.Author;
import com.example.hibernatelearn.domain.Book;
import com.example.hibernatelearn.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"package com.example.hibernatelearn.dao"} )
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDAOIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookRepository bookRepository;


    @Test
    void testAuthorByLastNameFindAll(){
        List<Author> author  = authorDao.findAllAuthorByLastName("Verstappen", PageRequest.of(0,1,
                Sort.by(Sort.Order.asc("lastName"))));
        assertThat(author.size()).isEqualTo(1);
        assertThat(author).isNotNull();

    }


    @Test
    void testCustomJpaNamedNativeQuery(){
        Book book = bookRepository.jpaNamed("Valley Of Fear");
        assertThat(book.getTitle()).isEqualTo("Valley Of Fear");
    }

    @Test
    void testCustomNativeQuery(){
        Book book = bookRepository.findBookWithNativeQuery("Valley Of Fear");
        assertThat(book.getTitle()).isEqualTo("Valley Of Fear");
    }

    @Test
    void testCustomParamNamedQuery(){
        Book book = bookRepository.findBookWithNamedQuery("Valley Of Fear");
        assertThat(book.getTitle()).isEqualTo("Valley Of Fear");

    }


    @Test
    void testCustomQuery(){
        Book book = bookRepository.findBookWithQuery("Valley Of Fear");
        assertThat(book.getTitle()).isEqualTo("Valley Of Fear");

    }

    @Test
    void testBookFuture() throws ExecutionException, InterruptedException {
        Future<Book> bookFuture = bookRepository.queryBookByTitle("Valley Of Fear");
        Book book = bookFuture.get();
        assertNotNull(book);
    }

    @Test
    void testBookStream(){
        AtomicInteger count = new AtomicInteger();

        bookRepository.findAllByTitleNotNull().forEach(book -> {
            count.incrementAndGet();
        });

        assertThat(count.get()).isGreaterThanOrEqualTo(1);
    }

    @Test
    void testEmptyResultException() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            Book book = bookRepository.readBookByTitle("myTitle");
        });
    }

    @Test
    void testNullParam() {
        assertNull(bookRepository.getBookByTitle(null));
    }

    @Test
    void testNoException() {
        assertNull(bookRepository.getBookByTitle("foog"));
    }
}