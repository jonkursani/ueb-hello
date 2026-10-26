package dev.jonkursani.hello.repository;

import dev.jonkursani.hello.domain.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
