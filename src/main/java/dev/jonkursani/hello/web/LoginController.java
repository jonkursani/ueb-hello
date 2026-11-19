package dev.jonkursani.hello.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // vetëm GET: POST /login e merr filtri i Spring Security
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
