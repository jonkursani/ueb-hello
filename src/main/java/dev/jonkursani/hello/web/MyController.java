package dev.jonkursani.hello.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @GetMapping("/my-controller")
    public String hello() {
        return "Hello my controller";
    }
}
