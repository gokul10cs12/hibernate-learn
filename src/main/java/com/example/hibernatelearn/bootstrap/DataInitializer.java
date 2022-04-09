package com.example.hibernatelearn.bootstrap;

import com.example.hibernatelearn.domain.AuthorUuid;
import com.example.hibernatelearn.domain.Book;
import com.example.hibernatelearn.domain.BookUuid;
import com.example.hibernatelearn.repositories.AuthorUuidRepository;
import com.example.hibernatelearn.repositories.BookRepository;
import com.example.hibernatelearn.repositories.BookUuidRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;

    public DataInitializer(BookRepository bookRepository, AuthorUuidRepository authorUuidRepository, BookUuidRepository bookUuidRepository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
        this.bookUuidRepository = bookUuidRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        bookRepository.deleteAll();

        Book myBook = new Book("Gokul's book", "123-213123", "Penguin", null);
        Book saveMyBook= bookRepository.save(myBook); // this saved object will have the Id properly generated.

//        System.out.println("The Id--->" + saveMyBook.getId());


        Book myNewBook = new Book("Rand's book", "123-213123", "Penguin", null);
        Book savedNewBook = bookRepository.save(myNewBook);
//        System.out.println("The 2nd Id--->" + savedNewBook.getId());

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book id-->" + book.getId() + ", Book name" + book.getTitle());
            System.out.println();
        });

        AuthorUuid authorUuid1 = new AuthorUuid();
        authorUuid1.setFirstName("myName");
        authorUuid1.setLastName("yourName");
        AuthorUuid authorUuidResponse = authorUuidRepository.save(authorUuid1);
        System.out.println("the generated uuid=" + authorUuidResponse.getId());

        BookUuid bookUuid = new BookUuid();
        bookUuid.setTitle("Gokul's Dream world");
        bookUuid.setPublisher("penguine");
        BookUuid bookUuidResponse = bookUuidRepository.save(bookUuid);
        System.out.println("the generated uuid before bytes=" + bookUuidResponse.getId());


    }
}
