package dev.jonkursani.hello.dto;

import dev.jonkursani.hello.domain.Author;
import dev.jonkursani.hello.domain.Quote;
import dev.jonkursani.hello.domain.Tag;

import java.util.ArrayList;
import java.util.List;

// Cka del jashte: autori vetem si id dhe emer, tag-et si tekst
public record QuoteResponse(Long id, String text, Integer year,
                            Long authorId, String authorName,
                            List<String> tags) {
    // entiteti -> DTO
    public static QuoteResponse from(Quote quote) {
        // 1. emrat e tag-eve, si tekst
        List<String> tagNames = new ArrayList<>();
        for (Tag tag : quote.getTags()) {
            tagNames.add(tag.getName());
        }
        // 2. autori: id dhe emri, pa listen e tij
        Author author = quote.getAuthor();
        return new QuoteResponse(quote.getId(), quote.getText(), quote.getYear(),
                author.getId(), author.getName(), tagNames);
    }
}
