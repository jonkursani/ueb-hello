package dev.jonkursani.hello.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.time.LocalDateTime;

// ki me pranu http requesta
@RestController
public class TimeController {
    private final Clock clock;

    public TimeController(Clock clock) {
        this.clock = clock;
    }

    @GetMapping("/time")
    public String time() {
        return "Ora ne Prishtine: " + LocalDateTime.now(clock).withNano(0);
    }
}
