package com.example.hibernatelearn.bootstrap;

import com.example.hibernatelearn.domain.Book;
import com.example.hibernatelearn.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

        System.out.println("The Id--->" + saveMyBook.getId());


        Book myNewBook = new Book("Rand's book", "123-213123", "Penguin");
        Book savedNewBook = bookRepository.save(myNewBook);
        System.out.println("The 2nd Id--->" + savedNewBook.getId());

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book id-->" + book.getId() + ", Book name" + book.getTitle());
            System.out.println();
        });

    }
}
