package dev.jonkursani.hello.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class EnglishGreeter implements Greeter {
    @Override
    public String greet(String name) {
        return "Hello %s!".formatted(name);
    }
}
