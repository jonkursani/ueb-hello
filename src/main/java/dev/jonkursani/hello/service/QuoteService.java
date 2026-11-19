package dev.jonkursani.hello.service;

import dev.jonkursani.hello.domain.Quote;
import dev.jonkursani.hello.domain.Tag;
import dev.jonkursani.hello.dto.QuoteRequest;
import dev.jonkursani.hello.dto.QuoteResponse;
import dev.jonkursani.hello.repository.AuthorRepository;
import dev.jonkursani.hello.repository.QuoteRepository;
import dev.jonkursani.hello.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Nje vend per rregullat: e thirrin edhe REST-i edhe faqet
@Service
@RequiredArgsConstructor
public class QuoteService {
    private final QuoteRepository quotes;
    private final AuthorRepository authors;
    private final TagRepository tags;

    @Transactional(readOnly = true)
    public List<QuoteResponse> findAll() {
        return toResponses(quotes.findAll(Sort.by("id")));
    }

    @Transactional(readOnly = true)
    public QuoteResponse findById(Long id) {
        return QuoteResponse.from(load(id));
    }

    @Transactional
    public QuoteResponse create(QuoteRequest request) {
        // 1. entitet i ri, id e jep databaza
        var quote = new Quote();
        // 2. fushat nga DTO
        apply(request, quote);
        // 3. ruaje, kthe DTO
        return QuoteResponse.from(quotes.save(quote));
    }

    @Transactional
    public QuoteResponse update(Long id, QuoteRequest request) {
        var quote = load(id);
        apply(request, quote);
        // 4. pa save: @Transactional e shkruan ndryshimin
        return QuoteResponse.from(quote);
    }

    @Transactional
    public void delete(Long id) {
        quotes.delete(load(id));
    }

    // entiteti -> QuoteRequest, per formen e edit-it
    @Transactional(readOnly = true)
    public QuoteRequest findForEdit(Long id) {
        var quote = load(id);
        // 1. tag-et si id
        Set<Long> tagIds = new HashSet<>();
        for (Tag tag : quote.getTags()) {
            tagIds.add(tag.getId());
        }
        // 2. autori si id
        Long authorId = quote.getAuthor().getId();
        return new QuoteRequest(quote.getText(), authorId, tagIds, quote.getYear());
    }

    // query-t qe i kishte QuoteController, tani me DTO

    @Transactional(readOnly = true)
    public List<QuoteResponse> findByAuthor(String author) {
        return toResponses(quotes.findByAuthorNameIgnoreCase(author));
    }

    @Transactional(readOnly = true)
    public List<QuoteResponse> search(String text) {
        return toResponses(quotes.findByTextContainingIgnoreCase(text));
    }

    @Transactional(readOnly = true)
    public long countByAuthor(String author) {
        return quotes.countByAuthorName(author);
    }

    @Transactional(readOnly = true)
    public QuoteResponse latest(String author) {
        return quotes.findFirstByAuthorNameIgnoreCaseOrderByIdDesc(author)
                .map(QuoteResponse::from)
                .orElseThrow(() -> new NotFoundException("Author not found"));
    }

    @Transactional(readOnly = true)
    public List<QuoteResponse> sortedBy(String by, String dir) {
        return toResponses(quotes.findAll(Sort.by(Sort.Direction.fromString(dir), by)));
    }

    @Transactional(readOnly = true)
    public Page<QuoteResponse> pages(int page, int size) {
        return quotes.findAll(PageRequest.of(page, size, Sort.by("id")))
                .map(QuoteResponse::from);
    }

    // per create dhe update: DTO -> entitet, ne nje vend
    private void apply(QuoteRequest request, Quote quote) {
        var author = authors.findById(request.authorId())
                .orElseThrow(() -> new NotFoundException("Author not found"));
        quote.setText(request.text());
        quote.setYear(request.year());
        quote.setAuthor(author);
        // tag-et: asnje, ose ato me keto id
        Set<Long> tagIds = request.tagIds() == null ? Set.of() : request.tagIds();
        quote.setTags(new HashSet<>(tags.findAllById(tagIds)));
    }

    private Quote load(Long id) {
        return quotes.findById(id)
                .orElseThrow(() -> new NotFoundException("Quote not found"));
    }

    private List<QuoteResponse> toResponses(List<Quote> list) {
        List<QuoteResponse> result = new ArrayList<>();
        for (Quote quote : list) {
            result.add(QuoteResponse.from(quote));
        }
        return result;
    }
}
