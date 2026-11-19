package dev.jonkursani.hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 1. rregullat: kush sheh çka
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/error", "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/pages/quotes", "/quotes/**").permitAll()
                .anyRequest().authenticated());
        // 2. login me faqen tonë; pas login-it, te lista
        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/pages/quotes"));
        // 3. Postman: Basic Auth
        http.httpBasic(Customizer.withDefaults());
        // 4. REST-i thirret nga Postman, jo nga forma
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/quotes/**"));
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        // 1. dy përdorues, vetëm në memorie
        UserDetails arta = User.withUsername("arta")
                .password("{noop}arta123")
                .roles("USER")
                .build();
        UserDetails besi = User.withUsername("besi")
                .password("{noop}besi123")
                .roles("USER")
                .build();
        // 2. Spring Security i kërkon këtu
        return new InMemoryUserDetailsManager(arta, besi);
    }
}
