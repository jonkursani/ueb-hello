package dev.jonkursani.hello.repository;

import dev.jonkursani.hello.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    // autoret bashke me thenien, ne nje select (pa 1 + N)
    @Query("select a from Author a left join fetch a.quotes order by a.name")
    List<Author> findAllWithQuotes();
}
