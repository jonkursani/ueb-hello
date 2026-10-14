package dev.jonkursani.hello.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Controller // kthen faqe - HTML
@RestController // kthen JSON (text)
public class HelloController {
    @GetMapping("/hello") // ?name=Filan
    public String hello(@RequestParam(defaultValue = "world") String name) {
//        return  "Hello, " + name + "!";
        return "Hello, %s!".formatted(name);
    }
}