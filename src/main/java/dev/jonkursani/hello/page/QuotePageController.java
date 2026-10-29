package dev.jonkursani.hello.page;

import dev.jonkursani.hello.domain.Quote;
import dev.jonkursani.hello.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//@RestController // API
@Controller // HTML (Thymeleaf)
@RequestMapping("/pages")
@RequiredArgsConstructor
public class QuotePageController {
    private final QuoteRepository repository;

    // GET /pages/quotes?author=Uvejs
    @GetMapping("/quotes")
    public String list(@RequestParam(required = false) String author, Model model) {
        List<Quote> quotes;

        if (author == null) {
            quotes = repository.findAll(Sort.by("id"));
        } else {
            quotes = repository.findByAuthorIgnoreCase(author);
        }

        model.addAttribute("quotes", quotes);
        return "quotes"; // templates/quotes.html
    }

    // GET /pages/quotes/1
    @GetMapping("/quotes/{id}")
    public String one(@PathVariable Long id, Model model) {
        var quote = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("quote", quote);

        return "quote"; // templates/quote.html
    }
}



























