package com.example.hibernatelearn.repositories;

import com.example.hibernatelearn.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Repository
public interface BookRepository extends JpaRepository<Book,Long > {

    Book jpaNamed(@Param("title")String title);

    @Query(value = "select * from Book where title=:title", nativeQuery = true)//native query
    Book findBookWithNativeQuery(@Param("title") String title);
    @Query("SELECT b FROM Book b where b.title= :title") //hql with named parameter query
    Book findBookWithNamedQuery(@Param("title") String title);

    @Query("SELECT b FROM Book b where b.title=?1") //hql
    Book findBookWithQuery(String title);

    Optional<Book> findBookByTitle(String title);

    Book readBookByTitle(String title);
    @Nullable
    Book getBookByTitle(@Nullable String title);

    Stream<Book> findAllByTitleNotNull();

    /*
    * The Future class represents a future result of an asynchronous computation.
    * This result will eventually appear in the Future after the processing is complete.
    * */
    @Async
    Future<Book> queryBookByTitle(String title);
}
