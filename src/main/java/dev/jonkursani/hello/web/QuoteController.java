package dev.jonkursani.hello.web;

import dev.jonkursani.hello.dto.QuoteRequest;
import dev.jonkursani.hello.dto.QuoteResponse;
import dev.jonkursani.hello.service.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotes")
@RequiredArgsConstructor
public class QuoteController {
    // controller-i njeh vetem service-in, jo repository-t
    private final QuoteService service;

    // GET /quotes
    @GetMapping
    public List<QuoteResponse> all() {
        return service.findAll();
    }

    // GET /quotes/1
    @GetMapping("/{id}")
    public QuoteResponse one(@PathVariable Long id) {
        return service.findById(id);
    }

    // POST /quotes {"text":"...","authorId":1,"tagIds":[2,3],"year":1597}
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuoteResponse create(@Valid @RequestBody QuoteRequest request) {
        return service.create(request);
    }

    // PUT /quotes/1
    @PutMapping("/{id}")
    public QuoteResponse update(@PathVariable Long id, @Valid @RequestBody QuoteRequest request) {
        return service.update(id, request);
    }

    // DELETE /quotes/1
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // GET /quotes/by-author?author=Jon
    @GetMapping("/by-author")
    public List<QuoteResponse> byAuthor(@RequestParam String author) {
        return service.findByAuthor(author);
    }

    // GET /quotes/search?text=Commit
    @GetMapping("/search")
    public List<QuoteResponse> search(@RequestParam String text) {
        return service.search(text);
    }

    // GET /quotes/count?author=Jon
    @GetMapping("/count")
    public long count(@RequestParam String author) {
        return service.countByAuthor(author);
    }

    // GET /quotes/latest?author=Jon
    @GetMapping("/latest")
    public QuoteResponse latest(@RequestParam String author) {
        return service.latest(author);
    }

    // GET /quotes/sorted-by?by=author&dir=desc
    @GetMapping("/sorted-by")
    public List<QuoteResponse> sortedBy(@RequestParam String by, @RequestParam String dir) {
        return service.sortedBy(by, dir);
    }

    // GET /quotes/pages?page=0&size=2
    @GetMapping("/pages")
    public Page<QuoteResponse> pages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return service.pages(page, size);
    }
}
