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
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveNewBook(book);
        bookDao.deleteBookById(saved.getId());
        Book deleted = bookDao.getById(saved.getId());
        assertThat(deleted).isNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthor(author);
        Book saved = bookDao.saveNewBook(book);

        saved.setTitle("New Book");
        bookDao.updateBook(saved);

        Book fetched = bookDao.getById(saved.getId());

        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");

        Author author = new Author();
        author.setId(3L);

        book.setAuthor(author);
        Book saved = bookDao.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByName() {
        Book book = bookDao.findBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getById(3L);

        assertThat(book.getId()).isNotNull();
    }


    @Test
    void testDeleteAuthorById(){
        Author author = new Author("Max", "Verstappen");
        Author saveResponse = authorDao.saveNewAuthor(author);
        assertThat(saveResponse.getFirstName()).isEqualTo("Max");

        authorDao.deleteAuthorById(saveResponse.getId());
        Author deleted = authorDao.getById(saveResponse.getId());
        assertThat(deleted).isNull();
    }

    @Test
    void testUpdateAuthor(){
        Author author = new Author("Will", "farrel");
        Author saveResponse = authorDao.saveNewAuthor(author);
        assertThat(saveResponse.getFirstName()).isEqualTo("Will");

        saveResponse.setLastName("Smith");;
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
    void testGetAuthor(){
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName(){
        Author author = authorDao.getAuthorByName("Charles", "Dickens");
        assertThat(author).isNotNull();

    }
}
