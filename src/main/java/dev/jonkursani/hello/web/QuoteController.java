package dev.jonkursani.hello.web;

import dev.jonkursani.hello.domain.Quote;
import dev.jonkursani.hello.repository.QuoteRepository;
import dev.jonkursani.hello.service.QuoteStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteController {
//    private final QuoteStore store;
    private final QuoteRepository store;

//    public QuoteController(QuoteStore store) {
//        this.store = store;
//    }

    // GET /quotes
    @GetMapping
    public List<Quote> all() {
        return store.findAll();
    }

    // GET /quotes/1
    @GetMapping("/{id}")
    public Quote one(@PathVariable Long id) {
        return store.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quote not found"));
    }

    // POST /quotes
    @PostMapping
    public Quote create(@RequestParam String author, @RequestParam String text) {
        var quote = new Quote();
        quote.setAuthor(author);
        quote.setText(text);
        return store.save(quote);
    }

    // DELETE /quotes/1
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        store.deleteById(id);
    }
}
