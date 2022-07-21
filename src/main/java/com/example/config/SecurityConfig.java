package com.example.config;

import com.example.security.AuthenticationManager;
import com.example.security.SecurityContextRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;

    //    @Bean
//    public ReactiveUserDetailsService userDetailsService() {
//        return repository::findByUsername;
//    }

//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    @Primary
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                ).accessDeniedHandler((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
                ).and()
                .csrf().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
//                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers( "/login-page", "/registration", "/login", "download-page").permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic().and()
                .formLogin().loginPage("/login")
                .and()
                .build();


//        http
//                .csrf()
//                .disable()
//                .authorizeExchange()
//                .pathMatchers("/registration")
//                .permitAll()
//                .and()
//                .authorizeExchange()
//                .pathMatchers("/upload-page")
//                .hasAnyRole("UPLOAD", "ADMIN")
//                .pathMatchers("/download-page")
//                .hasAnyRole("DOWNLOAD", "ADMIN")
//                .and()
//                .authorizeExchange()
//                .anyExchange()
//                .authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .formLogin();

//        return http.build();
    }

}

