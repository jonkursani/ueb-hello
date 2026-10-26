package dev.jonkursani.hello.service;

import dev.jonkursani.hello.domain.Quote;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteStore {
    private final List<Quote> quotes = new ArrayList<>();
    private long nextId = 1;

    public List<Quote> findAll() {
        return quotes;
    }

    public Quote save(Quote quote) {
        // long => smunet me kon null => 0L
        // Long => null
        if (quote.getId() == null) {
            quote.setId(nextId++);
            quotes.add(quote);
        }
        return quote;
    }

    public Optional<Quote> findById(Long id) {
        for (var quote : quotes) {
            if (quote.getId().equals(id)) {
                return Optional.of(quote);
            }
        }
        return Optional.empty();
    }

    public void deleteById(Long id) {
        quotes.removeIf(quote -> quote.getId().equals(id));
    }
}
