package dev.jonkursani.hello.config;

import dev.jonkursani.hello.domain.Author;
import dev.jonkursani.hello.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeedData implements CommandLineRunner {
    private final AuthorRepository authorRepository;

    @Override
    public void run(String... args) throws Exception {
        // Seed data here
        if (authorRepository.count() == 0) {
            String[] names = {"Xhema", "Leuk", "Lorik", "Drin"};

            for (String name : names) {
                var author = new Author();
                author.setName(name);
                authorRepository.save(author);
            }
        }
    }
}