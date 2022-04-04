package com.example.hibernatelearn;

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


    @Test
    void testMySQL(){
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }
}
