package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Author;
import com.example.hibernatelearn.domain.Book;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory entityManagerFactory;

    public AuthorDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public Author findAuthorByNameNative(String firstName, String lastName) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNativeQuery("SELECT * from author a where a.first_name =? and a.last_name =?", Author.class);

            query.setParameter(1, firstName);
            query.setParameter(2, lastName);

            return (Author) query.getSingleResult();
        } finally {
            em.close();
        }

    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Author> criteriaQuery = criteriaBuilder.createQuery(Author.class);

            Root<Author> root = criteriaQuery.from(Author.class);

            ParameterExpression<String> firstNameParam = criteriaBuilder.parameter(String.class);
            ParameterExpression<String> lastNameParam = criteriaBuilder.parameter(String.class);

            Predicate firstNamePred = criteriaBuilder.equal(root.get("firstName"), firstNameParam);
            Predicate lastNamePred = criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            criteriaQuery.select(root).where(criteriaBuilder.and(firstNamePred, lastNamePred));

            TypedQuery<Author> typedQuery = em.createQuery(criteriaQuery);
            typedQuery.setParameter(firstNameParam, firstName);
            typedQuery.setParameter(lastNameParam, lastName);

            return typedQuery.getSingleResult();
        } finally {
            em.close();
        }

    }

    @Override
    public Author findByFirstName(String firstName) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> query = em.createNamedQuery("find_by_first_name", Author.class);
            query.setParameter("first_name", firstName);
            return query.getSingleResult();
        }finally {
            em.close();
        }
    }

    /*
    * Using by Named query , query and name of the query are defined in the entity level  , i.e Author class.
    * */
    @Override
    public List<Author> findAll() {

        EntityManager em = getEntityManager();
        try {
            TypedQuery<Author> typedQuery = em.createNamedQuery("author_find_all", Author.class);
            return typedQuery.getResultList();

        }finally {
            em.close();
        }
    }

    @Override
    public List<Author> listAuthorByLastNameLik(String lastName) {
        EntityManager em =getEntityManager();
        try{
            Query query = em.createQuery("select a from Author a where a.lastName like :last_name");
            query.setParameter("last_name", lastName + "%");
//            List<Author> authors = query.getResultList();
            return query.getResultList();
        } finally {
            em.close();
        }
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
