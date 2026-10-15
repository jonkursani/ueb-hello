package dev.jonkursani.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfig {
    @Bean // kur klasen ose interface nuk e ki kriju ti
    public Clock clock() {
        return Clock.system(ZoneId.of("Europe/Tirane"));
    }
}
