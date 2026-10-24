package dev.jonkursani.hello.domain;

import jakarta.persistence.*;
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

    // constraints ne kolonen e tabeles
    // nullable = false => NOT NULL
    // length => varchar(300)
    @Column(nullable = false, length = 300)
    private String text;

    // nullable = true, length = 255
    private String author;

    LocalDateTime createdAt;

    //    @NoArgsConstructor
//    public Quote() {}
}