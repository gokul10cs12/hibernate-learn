package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Author;
import com.example.hibernatelearn.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao{

    private final AuthorRepository authorRepository;


    @Autowired
    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    @Override
    public List<Author> findAllAuthorByLastName(String lastName, Pageable pageable) {
        return authorRepository.findAuthorByLastName(lastName, pageable).getContent();
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.getById(id);
    }

    @Override
    public Author getAuthorByName(String firstName, String lastName) {
        return authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new); // learn this.
    }


    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    /*
     * Without the annotation, you lose the advantages of the transactions like the rollbacks.
     * With the @Transactional annotation you are doing more than one database operation, like many inserts and one fails,
     * all the operations in the transaction can rollback to give consistency to your data.
     * If not defined, every call to repository will execute/run in separate context
     * */
    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        Author author1 = authorRepository.getById(author.getId());
        author1.setFirstName(author.getFirstName());
        author1.setLastName(author.getLastName());
        return authorRepository.save(author1);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
