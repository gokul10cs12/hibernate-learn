package com.example.hibernatelearn.dao;

import com.example.hibernatelearn.domain.Book;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class BookDaoHibernate implements BookDao {
    private final EntityManagerFactory entityManagerFactory;

    public BookDaoHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        EntityManager em = getEntityManager();
        try{
            TypedQuery<Book> query = em.createQuery("Select b from Book b", Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        }finally {
            em.close();
        }
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return null;
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return null;
    }

    @Override
    public List<Book> findAllBooks() {
        return null;
    }

    @Override
    public Book findByISBN(String isbn) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery("select a from Book a where a.isbn = :isbn", Book.class);
            query.setParameter("isbn", isbn);

            Book book = query.getSingleResult();
            return book;
        }finally {
            em.close();
        }
    }

    @Override
    public Book getById(Long id) {
        return getEntityManager().find(Book.class, id);
    }

    @Override
    public Book findBookByTitle(String title) {
        TypedQuery<Book> query = getEntityManager().createQuery("select a from Book a where a.title = :title", Book.class);
        query.setParameter("title", title);
        return query.getSingleResult();
    }

    @Override
    public Book saveNewBook(Book book) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return entityManager.find(Book.class, book.getId());
    }

    @Override
    public Book updateBook(Book book) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.flush();
        em.clear();
        return em.find(Book.class, book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Book findBook = em.find(Book.class, id);
        em.remove(findBook);
        em.flush();
        em.getTransaction().commit();

    }

    EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
