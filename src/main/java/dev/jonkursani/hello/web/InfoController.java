package dev.jonkursani.hello.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    private final String appName;

    // application.properties => @Value("${}")
    public InfoController(@Value("${spring.application.name}") String appName) {
        this.appName = appName;
    }

    @GetMapping("/info")
    public String info() {
        return "App: " + appName;
    }
}














