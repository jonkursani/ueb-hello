package dev.jonkursani.hello.web;

import dev.jonkursani.hello.domain.Quote;
import dev.jonkursani.hello.repository.QuoteRepository;
import dev.jonkursani.hello.service.QuoteStore;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    // GET /quotes/by-author?author=Jon
    @GetMapping("/by-author")
    public List<Quote> byAuthor(@RequestParam String author) {
//        return store.findByAuthor(author);
        return store.findByAuthorIgnoreCase(author);
    }

    // GET /quotes/search?text=Commit
    @GetMapping("/search")
    public List<Quote> search(@RequestParam String text) {
        return store.findByTextContainingIgnoreCase(text);
    }

    // GET /quotes/count?author=Jon
    @GetMapping("/count")
    public long count(@RequestParam String author) {
        return store.countByAuthor(author);
    }

    // GET /quotes/latest?author=Jon
    @GetMapping("/latest")
    public Quote latest(@RequestParam String author) {
        return store.findFirstByAuthorIgnoreCaseOrderByIdDesc(author)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }

    // GET /quotes/sorted-by?by=author&dir=desc
    @GetMapping("/sorted-by")
    public List<Quote> sortedBy(@RequestParam String by, @RequestParam String dir) {
        Sort.Direction direction = Sort.Direction.fromString(dir);
        Sort sort = Sort.by(direction, by);
        return store.findAll(sort);
    }

    // GET /quotes/pages?page=0&size=2
    @GetMapping("/pages")
    public Page<Quote> pages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Quote> result = store.findAll(pageable);
        return result;
    }
}