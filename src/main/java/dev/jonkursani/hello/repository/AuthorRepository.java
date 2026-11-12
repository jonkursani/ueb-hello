package dev.jonkursani.hello.repository;

import dev.jonkursani.hello.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
