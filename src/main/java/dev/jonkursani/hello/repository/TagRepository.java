package dev.jonkursani.hello.repository;

import dev.jonkursani.hello.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
