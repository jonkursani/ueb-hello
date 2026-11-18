package dev.jonkursani.hello.page;

import dev.jonkursani.hello.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/pages")
@RequiredArgsConstructor
public class AuthorPageController {
    private final AuthorRepository repository;

    // GET /pages/authors => autoret me numrin e thenieve, me nje select
    @GetMapping("/authors")
    public String list(Model model) {
        model.addAttribute("authors", repository.findAllWithQuotes());
        return "authors"; // templates/authors.html
    }

    // GET /pages/authors/1 => autori me thenien e tij (mappedBy)
    @GetMapping("/authors/{id}")
    public String one(@PathVariable Long id, Model model) {
        var author = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("author", author);
        return "author"; // templates/author.html
    }
}
