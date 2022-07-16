package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

//    @Bean
//    public ReactiveUserDetailsService userDetailsService() {
//        return repository::findByUsername;
//    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf()
                .disable()
                .authorizeExchange()
                .pathMatchers("/download-page", "/upload-page")
                .hasAuthority("ROLE_ADMIN")
                .pathMatchers("/download-page")
                .hasAuthority("ROLE_DOWNLOAD")
                .pathMatchers("/upload-page")
                .hasAuthority("ROLE_UPLOAD")
                .anyExchange()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin();
        return http.build();
    }


}
