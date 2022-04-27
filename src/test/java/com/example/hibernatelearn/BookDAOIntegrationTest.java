package com.example.hibernatelearn;

import com.example.hibernatelearn.dao.AuthorDao;
import com.example.hibernatelearn.dao.BookDao;
import com.example.hibernatelearn.dao.BookJdbcTemplate;
import com.example.hibernatelearn.domain.Author;
import com.example.hibernatelearn.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"package com.example.hibernatelearn.dao"} )
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
public class BookDAOIntegrationTest {

    AuthorDao authorDao;

    BookDao bookDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(){
        bookDao = new BookJdbcTemplate(jdbcTemplate);
    }


    /*
     *  with pageable sort amd page, size settings.
     * */
    @Test
    void testFindAllBooksPage_SortByTitlePageable(){
        List<Book> books = bookDao.findAllBookSortByTitle(PageRequest.of(0,2,
                Sort.by(Sort.Order.asc("title"))
        ));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }


    @Test
    void testFindAllBooksPagePageable(){
        List<Book> books = bookDao.findAllBook(PageRequest.of(0,2));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void testFindAllBooksPageTwoPageable(){
        List<Book> books = bookDao.findAllBook(PageRequest.of(1,2));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void testFindAllBooksPageThreePageable(){
        List<Book> books = bookDao.findAllBook(PageRequest.of(2,30));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0); // if the offset value exceeds that actual records.
    }



/*
*  normal page offset implementation
* */

    @Test
    void testFindAllBooksPage(){
        List<Book> books = bookDao.findAllBooks(2,0);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void testFindAllBooksPageTwo(){
        List<Book> books = bookDao.findAllBooks(2,2);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void testFindAllBooksPageThree(){
        List<Book> books = bookDao.findAllBooks(2,12);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(0); // if the offset value exceeds that actual records.
    }



    @Test
    void testFindAllBooks(){
        List<Book> book = bookDao.findAllBook();
        assertThat(book).isNotNull();
        assertThat(book.size()).isGreaterThan(1);
    }


    @Test
    void deleteBookRecordById(){
        Book newBook = new Book(
                "thisIsIt",
                "The killer",
                "DCB",
                2L
        );
        Book newBookResponse = bookDao.saveNewBook(newBook);
        bookDao.deleteBookById(newBookResponse.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> bookDao.getById(newBookResponse.getId()));

    }


    @Test
    void testUpdateBookDetails(){
        Book myBook = new Book(
                "thisIsIt",
                "The killer",
                "DCB",
                1L
        );
    Book saveNewResponse =  bookDao.saveNewBook(myBook);
    saveNewResponse.setTitle("GoodFellas");
    saveNewResponse.setPublisher("WB");
    Book updateNewBookResponse = bookDao.updateBook(saveNewResponse);
    assertThat(updateNewBookResponse.getTitle()).isEqualTo("GoodFellas");
    }

    @Test
    void testSaveNewBook(){
        Book myBook = new Book(
                "abccd",
                "Kill till I die",
                "DCB",
                12344L
        );

        Book newBookResponse = bookDao.saveNewBook(myBook);
        assertThat(newBookResponse.getPublisher()).isEqualTo("DCB");
    }

    @Test
    void testGetBookByTitle(){
        String titleName ="myBook";
        Book book= bookDao.findBookByTitle(titleName);
        assertThat(book).isNotNull();
    }

    @Test
    void testGetBookById(){
        String penguinPublisher="penguin";
        Book book = bookDao.getById(1L);
        assertThat(book.getPublisher()).isEqualTo(penguinPublisher);
    }

    @Test
    void testDeleteAuthorById(){
        Author author = new Author("Max", "Verstappen");
        Author saveResponse = authorDao.saveNewAuthor(author);
        assertThat(saveResponse.getFirstName()).isEqualTo("Max");

        authorDao.deleteAuthorById(saveResponse.getId());
        // making sure an error occurred while querying the invalid id
        assertThrows(EmptyResultDataAccessException.class, ()-> authorDao.getById(saveResponse.getId()));
    }

    @Test
    void testUpdateAuthor(){
        Author author = new Author("Will", "farrel");
        Author saveResponse = authorDao.saveNewAuthor(author);
        assertThat(saveResponse.getFirstName()).isEqualTo("Will");

        saveResponse.setLastName("Smith");
        Author updateResponse = authorDao.updateAuthor(saveResponse);
        assertThat(updateResponse.getLastName()).isEqualTo("Smith");

    }

    @Test
    void testAddNewAuthor(){
        Author author = new Author("Charles", "Dickens");
        Author saveResponse = authorDao.saveNewAuthor(author);
        assertThat(saveResponse).isNotNull();
    }

    @Test
    void testGetAuthorById(){
        Author author = authorDao.getById(7L);
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName(){
        Author author = authorDao.getAuthorByName("Gokul", "nb");
        assertThat(author).isNotNull();

    }
}
