package dev.jonkursani.hello.page;

import dev.jonkursani.hello.domain.Quote;
import dev.jonkursani.hello.repository.QuoteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // E shfaq formen qe me i mush te dhenat user-i
    @GetMapping("/quotes/new")
    public String newForm(Model model) {
        model.addAttribute("quote", new Quote());
        return "quote-form"; // templates/quote-form.html
    }

    @PostMapping("/quotes")
    public String create(@Valid @ModelAttribute("quote") Quote quote,
                         BindingResult result,
                         RedirectAttributes flash) {

        // @Valid i tregon qe me aktivizu validimin
        // Errorat mapohen ne BindingResult
        // nese ka errora kthehu prap te forma per me i pa gabimet
        if (result.hasErrors()) {
            return "quote-form";
        }

        // quote: { text: '', author: '' }
        repository.save(quote);

        flash.addFlashAttribute("message", "Quote created successfully!");

        return "redirect:/pages/quotes"; // redirect te lista me i pa ndryshimet
    }
}



























