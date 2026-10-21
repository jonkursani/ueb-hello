package dev.jonkursani.hello.service;

import dev.jonkursani.hello.config.GreetingProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j // logging (printim i mesazheve)
@Service
@RequiredArgsConstructor // konstruktori me parametra edhe me inicializim te dependency
public class AlbanianGreeter implements Greeter {
    private final GreetingProperties props;

    // zevendesohet prej @RequiredArgsConstructor
//    public AlbanianGreeter(GreetingProperties props) {
//        this.props = props;
//    }

    @Override
    public String greet(String name) {
//        return "Pershendetje %s!".formatted(name);
        log.info("Greeting {}", name);
        return props.template().formatted(name);
    }
}