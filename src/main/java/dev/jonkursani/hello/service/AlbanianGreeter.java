package dev.jonkursani.hello.service;

import org.springframework.stereotype.Service;

@Service
public class AlbanianGreeter implements Greeter {
    @Override
    public String greet(String name) {
        return "Pershendetje %s!".formatted(name);
    }
}
