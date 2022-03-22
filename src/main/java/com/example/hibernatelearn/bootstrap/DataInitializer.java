package com.example.hibernatelearn.bootstrap;

import com.example.hibernatelearn.domain.Book;
import com.example.hibernatelearn.repositories.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Book myBook = new Book("Gokul's book", "123-213123", "Penguin");
        Book saveMyBook= bookRepository.save(myBook); // this saved object will have the Id properly generated.


        Book myBook = new Book("Rand's book", "123-213123", "Penguin");

    }
}
