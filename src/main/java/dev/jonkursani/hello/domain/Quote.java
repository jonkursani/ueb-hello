package dev.jonkursani.hello.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity // JPA
@Getter // Lombok
@Setter // Lombok
@NoArgsConstructor // Lombok - konstruktori pa parametra
public class Quote {
    @Id // JPA - primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA - auto increment
    private Long id;

    @NotBlank(message = "Quote is required")
    @Size(min = 5, max = 300, message = "Quote: {min} to {max} character") // perdoret per tekst
    // constraints ne kolonen e tabeles
    // nullable = false => NOT NULL
    // length => varchar(300)
    @Column(nullable = false, length = 300)
    private String text;

    // nullable = true, length = 255
    @NotBlank(message = "Author is required")
    private String author;

    // nese te duhet me validu numra (@Min, @Max)
    @Min(value = 0, message = "Year cannot be negative")
    @Max(2026) // message default => must be less than or equal to 2026
    private Integer year;

    LocalDateTime createdAt;

    //    @NoArgsConstructor
//    public Quote() {}
}