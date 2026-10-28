package dev.jonkursani.hello.config;

import dev.jonkursani.hello.repository.QuoteRepository;
import org.springframework.aop.support.AopUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyProbe {
    // shembull i klases qe krijohet prej JPA qe na se shohim qe i permban metodat (sosht mandatory mu kriju)
    @Bean
    public CommandLineRunner probe(QuoteRepository store) {
        return args -> {
            System.out.println(store.getClass().getName());
            System.out.println(AopUtils.getTargetClass(store).getName());
        };
    }
}
