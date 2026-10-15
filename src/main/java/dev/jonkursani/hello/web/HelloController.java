package dev.jonkursani.hello.web;

import dev.jonkursani.hello.service.Greeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@Controller // kthen faqe - HTML
@RestController // kthen JSON (text)
public class HelloController {
    // DI
    // field (property)
    private final Greeter greeter; // new AlbanianGreeter();

//    @Autowired
    public HelloController(@Qualifier("albanianGreeter") Greeter greeter) {
        this.greeter = greeter;
    }

//    @Autowired
//    public void setDependency(String name) {
//
//    }

    @GetMapping("/hello") // ?name=Filan
    public String hello(@RequestParam(defaultValue = "world") String name) {
//        return  "Hello, " + name + "!";
//        return "Hello, %s!".formatted(name);
        return greeter.greet(name);
    }
}