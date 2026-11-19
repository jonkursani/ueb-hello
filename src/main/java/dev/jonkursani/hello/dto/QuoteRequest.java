package dev.jonkursani.hello.dto;

import dev.jonkursani.hello.validation.NotFutureYear;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

// Cka hyn brenda: pa id, autori vetem si numer
public record QuoteRequest(
        @NotBlank(message = "Quote is required")
        @Size(min = 5, max = 300, message = "Quote: {min} to {max} character")
        String text,

        @NotNull(message = "Author is required")
        Long authorId,

        Set<Long> tagIds,

        @Min(value = 0, message = "Year cannot be negative")
        @NotFutureYear
        Integer year) {
}
