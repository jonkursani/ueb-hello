package dev.jonkursani.hello.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class AboutPageController {
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}