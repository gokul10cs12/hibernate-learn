package com.example.hibernatelearn;

import com.example.hibernatelearn.dao.AuthorDao;
import com.example.hibernatelearn.domain.Author;
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
