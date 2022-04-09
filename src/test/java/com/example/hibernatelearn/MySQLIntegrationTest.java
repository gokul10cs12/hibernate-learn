package com.example.hibernatelearn;

import com.example.hibernatelearn.domain.BookNatural;
import com.example.hibernatelearn.domain.composite.AuthorComposite;
import com.example.hibernatelearn.domain.composite.AuthorEmbedded;
import com.example.hibernatelearn.domain.composite.NameId;
import com.example.hibernatelearn.repositories.AuthorCompositeRepository;
import com.example.hibernatelearn.repositories.AuthorEmbeddedRepository;
import com.example.hibernatelearn.repositories.BookNaturalRepository;
import com.example.hibernatelearn.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
/*
* You assign profile to your components with @Profile; while testing select them with @ActiveProfiles,
* while developing select them with spring.profiles.active property.
* */
@DataJpaTest //test splice only to test the db layer of the project, very minimal configuration
@ComponentScan(basePackages = {"com.example.hibernatelearn.bootstrap"}) //add the component to the context
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // usually spring overrides the given profile
//config with h2 configs we add autoconfigureTestDataBase to none, to avoid doing this by Spring.
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Autowired
    AuthorCompositeRepository authorCompositeRepository;

    @Autowired
    AuthorEmbeddedRepository authorEmbeddedRepository;

    @Test
    void authorEmbeddedTest(){
        NameId nameId= new NameId("Map", "NB");
        AuthorEmbedded authorEmbedded =new AuthorEmbedded(nameId);

        assertThat(authorEmbedded.getNameId()).isNotNull();

        AuthorEmbedded authorEmbeddedResponse = authorEmbeddedRepository.save(authorEmbedded);
        assertThat(authorEmbeddedResponse).isNotNull();
        AuthorEmbedded fetched = authorEmbeddedRepository.getById(nameId);
        assertThat(fetched).isNotNull();

    }


    @Test
    void authorCompositeTest(){
        NameId nameId = new NameId("Gokul", "NB");
        AuthorComposite authorComposite = new AuthorComposite();
        authorComposite.setFirstName(nameId.getFirstName());
        authorComposite.setLastName(nameId.getLastName());
        authorComposite.setCountry("India");

        AuthorComposite savedDataResponse = authorCompositeRepository.save(authorComposite);
        assertThat(savedDataResponse).isNotNull();

        AuthorComposite fetched = authorCompositeRepository.getById(nameId); // calling by type nameId.
        assertThat(fetched).isNotNull();

    }

    @Test
    void bookNaturalTest(){
        BookNatural bookNatural= new BookNatural();
        bookNatural.setTitle("MyTitle");
        BookNatural bookNatural1 = bookNaturalRepository.save(bookNatural);
        BookNatural fetched = bookNaturalRepository.getById(bookNatural1.getTitle());
        assertThat(fetched).isNotNull();

    }

    @Test
    void testMySQL(){
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }
}
