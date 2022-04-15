package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Author;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory entityManagerFactory;

    public AuthorDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Author getById(Long id) {
        return getEntityManager().find(Author.class, id);
    }

    @Override
    public Author getAuthorByName(String firstName, String lastName) {
        TypedQuery<Author> query = getEntityManager().createQuery("SELECT a from Author a" +
                " where a.firstName= :first_name and a.lastName = :last_name", Author.class);

        query.setParameter("first_name", firstName);
        query.setParameter("last_name", lastName);


        return query.getSingleResult();
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(author);
        em.flush(); //A call to EntityManager.flush(); will force the data to be persist in the database immediately as EntityManager.persist()
        //i.e make the changes effective to DB before committing.
        em.getTransaction().commit(); //persisting the data in db

        return author;

    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager em = getEntityManager();

        em.getTransaction().begin();
        /*
               merge(author) can insert new objects and update existing ones. i.e existing entity and update the same.
        * Merge returns the managed instance that the state was merged with. It does return something that exists in PersistenceContext or creates a new instance of your entity.
        * In any case, it will copy the state from the supplied entity, and return a managed copy.
         * */
        em.merge(author);
        em.flush(); //execute sql transaction against the database.
        em.clear(); //clear the first level cache and force hibernate to fetch the entity again.
        return em.find(Author.class, author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Author author = em.find(Author.class, id);
        em.remove(author);
        em.flush();
        em.getTransaction().commit();

    }
    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
