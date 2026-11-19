package dev.jonkursani.hello.page;

import dev.jonkursani.hello.domain.Author;
import dev.jonkursani.hello.domain.Tag;
import dev.jonkursani.hello.dto.QuoteRequest;
import dev.jonkursani.hello.dto.QuoteResponse;
import dev.jonkursani.hello.repository.AuthorRepository;
import dev.jonkursani.hello.repository.TagRepository;
import dev.jonkursani.hello.service.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;

//@RestController // API
@Controller // HTML (Thymeleaf)
@RequestMapping("/pages")
@RequiredArgsConstructor
public class QuotePageController {
    private final QuoteService service;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;

    // ne model per cdo faqe te ketij controller-i (edhe kur forma kthehet me gabime)
    @ModelAttribute("authors")
    public List<Author> authors() {
        return authorRepository.findAll();
    }

    @ModelAttribute("tags")
    public List<Tag> tags() {
        return tagRepository.findAll();
    }

    // GET /pages/quotes?author=Uvejs
    @GetMapping("/quotes")
    public String list(@RequestParam(required = false) String author, Model model) {
        List<QuoteResponse> quotes;

        if (author == null) {
            quotes = service.findAll();
        } else {
            quotes = service.findByAuthor(author);
        }

        model.addAttribute("quotes", quotes);
        return "quotes"; // templates/quotes.html
    }

    // GET /pages/quotes/1
    @GetMapping("/quotes/{id}")
    public String one(@PathVariable Long id, Model model) {
        model.addAttribute("quote", service.findById(id));
        return "quote"; // templates/quote.html
    }

    // E shfaq formen qe me i mush te dhenat user-i
    @GetMapping("/quotes/new")
    public String newForm(Model model) {
        // pa quoteId ne model => forma e di qe eshte "e re"
        model.addAttribute("quote", new QuoteRequest(null, null, new HashSet<>(), null));
        return "quote-form"; // templates/quote-form.html
    }

    @PostMapping("/quotes")
    public String create(@Valid @ModelAttribute("quote") QuoteRequest quote,
                         BindingResult result,
                         RedirectAttributes flash) {

        // @Valid i tregon qe me aktivizu validimin
        // Errorat mapohen ne BindingResult
        // nese ka errora kthehu prap te forma per me i pa gabimet
        if (result.hasErrors()) {
            return "quote-form";
        }

        service.create(quote);

        flash.addFlashAttribute("message", "Quote created successfully!");

        return "redirect:/pages/quotes"; // redirect te lista me i pa ndryshimet
    }

    @GetMapping("/quotes/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("quote", service.findForEdit(id));
        // QuoteRequest s'ka id, prandaj forma e merr vec
        model.addAttribute("quoteId", id);
        return "quote-form"; // templates/quote-form.html
    }

    @PostMapping("/quotes/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("quote") QuoteRequest quote,
                         BindingResult result,
                         Model model,
                         RedirectAttributes flash) {

        if (result.hasErrors()) {
            model.addAttribute("quoteId", id); // forma me gabime ka nevoje prape per id
            return "quote-form";
        }

        service.update(id, quote);

        flash.addFlashAttribute("message", "Quote updated successfully!");

        return "redirect:/pages/quotes"; // redirect te lista me i pa ndryshimet
    }

    @PostMapping("/quotes/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        service.delete(id);
        flash.addFlashAttribute("message", "Quote deleted successfully!");
        return "redirect:/pages/quotes";
    }
}
