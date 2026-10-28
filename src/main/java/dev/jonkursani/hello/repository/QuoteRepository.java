package dev.jonkursani.hello.repository;

import dev.jonkursani.hello.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    // findAll()
    // findById()
    // save()
    // deleteById()

    // select * from quote where author = ?
    List<Quote> findByAuthor(String author);
    List<Quote> findByAuthorIgnoreCase(String author);

    // select * from quote where text like ?
    List<Quote> findByTextContainingIgnoreCase(String text);

    // select count(*) from quote where author = ?
    long countByAuthor(String author);

    // select * from quote where author = ? order by id desc limit 1
    Optional<Quote> findFirstByAuthorOrderByIdDesc(String author);
    Optional<Quote> findFirstByAuthorIgnoreCaseOrderByIdDesc(String author);

    // JPQL => Jakarta Persistence Query Language
    @Query("select q from Quote q where length(q.text) <= :max order by q.id")
    List<Quote> findShort(@Param("max") int max);
}