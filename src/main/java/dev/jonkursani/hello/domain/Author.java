package dev.jonkursani.hello.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required") // per client
    @Column(nullable = false, length = 100) // per DB
    private String name;

    // nje autor -> shume thenie; kolona author_id eshte te Quote.author
    @OneToMany(mappedBy = "author")
    private List<Quote> quotes = new ArrayList<>();
}