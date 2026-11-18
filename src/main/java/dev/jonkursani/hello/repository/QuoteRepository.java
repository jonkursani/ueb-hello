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

    // select * from quote where author.name = ?
    List<Quote> findByAuthorName(String author);
    List<Quote> findByAuthorNameIgnoreCase(String author);

    // select * from quote where text like ?
    List<Quote> findByTextContainingIgnoreCase(String text);

    // select count(*) from quote where author.name = ?
    long countByAuthorName(String author);

    // select * from quote where author.name = ? order by id desc limit 1
    Optional<Quote> findFirstByAuthorNameOrderByIdDesc(String author);
    Optional<Quote> findFirstByAuthorNameIgnoreCaseOrderByIdDesc(String author);

    // JPQL => Jakarta Persistence Query Language
    @Query("select q from Quote q where length(q.text) <= :max order by q.id")
    List<Quote> findShort(@Param("max") int max);
}