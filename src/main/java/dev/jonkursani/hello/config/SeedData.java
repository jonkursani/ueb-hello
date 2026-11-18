package dev.jonkursani.hello.config;

import dev.jonkursani.hello.domain.Author;
import dev.jonkursani.hello.domain.Tag;
import dev.jonkursani.hello.repository.AuthorRepository;
import dev.jonkursani.hello.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeedData implements CommandLineRunner {
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;

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

        if (tagRepository.count() == 0) {
            String[] names = {"dija", "leximi", "jeta", "programimi"};

            for (String name : names) {
                var tag = new Tag();
                tag.setName(name);
                tagRepository.save(tag);
            }
        }
    }
}
