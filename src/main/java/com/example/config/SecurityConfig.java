package com.example.config;

import com.example.repo.PeopleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig  {

    private final PeopleRepository repository ;

    public SecurityConfig(PeopleRepository repository) {
        this.repository = repository;
    }

//    @Bean
//    public ReactiveUserDetailsService userDetailsService() {
//        return repository::findByUsername;
//    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin();
        return http.build();
    }


}
