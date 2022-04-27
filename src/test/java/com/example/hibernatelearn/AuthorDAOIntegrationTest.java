package com.example.hibernatelearn;

import com.example.hibernatelearn.dao.AuthorDao;
import com.example.hibernatelearn.dao.BookDao;
import com.example.hibernatelearn.dao.BookDaoHibernate;
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
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

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
    EntityManagerFactory entityManagerFactory;

    BookDao bookDao;

    @BeforeEach
    void setUp(){
        bookDao = new BookDaoHibernate(entityManagerFactory);
    }

    @Test
    void testFindBooksSortByTitle(){
        List<Book> books = bookDao.findAllBooksSortByTitle(PageRequest.of(0, 7,
                Sort.by(Sort.Order.desc("title"))));
        assertThat(books.size()).isNotNull();
        assertThat(books.size()).isEqualTo(7);
    }


    @Test
    void testFindBooksByTitle(){
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 2));
        assertThat(books.size()).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void testAuthorByNameNative(){
        Author author =authorDao.findAuthorByNameNative("Sam", "Smith");
        assertThat(author.getFirstName()).isEqualTo("Sam");
    }

    @Test
    void testAuthorByNameCriteria(){
        Author author =authorDao.findAuthorByNameCriteria("Sam", "Smith");
        assertThat(author.getFirstName()).isEqualTo("Sam");
    }

    @Test
    void testFindByFirstName(){
        Author author = new Author();
        author.setFirstName("Sam");
        author.setLastName("Smith");
        Author savedAuthor = authorDao.saveNewAuthor(author);
        Author foundAuthor = authorDao.findByFirstName(savedAuthor.getFirstName());
        assertThat(foundAuthor.getFirstName()).isEqualTo(savedAuthor.getFirstName());
    }

    @Test
    void testFindAllAuthors(){
        List<Author> authors= authorDao.findAll();
        assertThat(authors.size()).isGreaterThan(1);
    }

    @Test
    void testFindByISBN(){
        Book book = new Book();
        book.setIsbn("abc");
        book.setAuthorId(12L);
        book.setPublisher("penguin");
        book.setTitle("Valley Of Fear");

        Book savedBook = bookDao.saveNewBook(book);
        Book findBookByISBN = bookDao.findByISBN(savedBook.getIsbn());

        assertThat(findBookByISBN.getIsbn()).isEqualTo(book.getIsbn());

    }

    @Test
    void testListAuthorByLastNameLik(){
        List<Author> authors = authorDao.listAuthorByLastNameLik("Will");
        assertThat(authors.get(0).getLastName()).isEqualTo("Will");
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Test
    void testDeleteBookRecordById(){
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
