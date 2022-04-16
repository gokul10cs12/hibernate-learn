package com.example.hibernatelearn;

import com.example.hibernatelearn.dao.AuthorDao;
import com.example.hibernatelearn.dao.BookDao;
import com.example.hibernatelearn.domain.Author;
import com.example.hibernatelearn.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"package com.example.hibernatelearn.dao"} )
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDAOIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

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
        Book checkDeletedBook = bookDao.getById(newBookResponse.getId());
        assertThat(checkDeletedBook).isNull();
        assertThat(authorDao.getById(newBookResponse.getId()));
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
        String titleName ="papillon";
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
        Author deletedAuthor = authorDao.getById(saveResponse.getId());
        // making sure an error occurred while querying the invalid id
        assertThat(deletedAuthor).isNull();
        assertThat(authorDao.getById(saveResponse.getId()));
    }

    @Test
    void testUpdateAuthor(){
        Author author = new Author("Will", "farrel");
        Author saveResponse = authorDao.saveNewAuthor(author);
        assertThat(saveResponse.getFirstName()).isEqualTo("Will");

        saveResponse.setLastName("Smith");
        saveResponse.setFirstName("Adam");
        Author updateResponse = authorDao.updateAuthor(saveResponse);
        assertThat(updateResponse.getLastName()).isEqualTo("Smith");
        assertThat(updateResponse.getFirstName()).isEqualTo("Adam");

    }

    @Test
    void testAddNewAuthor(){
        Author author = new Author("Charles", "Dickens");
        Author saveResponse = authorDao.saveNewAuthor(author);
        assertThat(saveResponse).isNotNull();
        assertThat(saveResponse.getId()).isNotNull();
    }

    @Test
    void testGetAuthorById(){
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName(){
        Author author = authorDao.getAuthorByName("Gokul", "nb");
        assertThat(author).isNotNull();

    }
}
