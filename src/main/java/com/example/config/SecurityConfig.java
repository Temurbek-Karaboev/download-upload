package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public SecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                                )
                )
                .accessDeniedHandler(
                        (swe, e) ->
                                Mono.fromRunnable(
                                                () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                                        ))
                                        .and()
                                        .csrf()
                                        .disable()
                                        .authenticationManager(authenticationManager)
                                        .securityContextRepository(securityContextRepository)
                                        .authorizeExchange()
                                        .pathMatchers("/registration")
                                        .permitAll()
                                        .and()
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
