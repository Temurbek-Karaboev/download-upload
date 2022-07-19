package com.example.controllers;

import com.example.models.Person;
import com.example.service.PersonService;
import com.example.util.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
public class UserController {
    private final PersonService personService;
    private final JWTUtil jwtUtil;

    public UserController(PersonService personService, JWTUtil jwtUtil) {
        this.personService = personService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity> login (ServerWebExchange swe){
        return swe.getFormData().flatMap(credentials ->
                personService.findByUsername(credentials.getFirst("username"))
                .cast(Person.class)
                .map(userDetails ->
                        Objects.equals(
                                credentials.getFirst("password"),
                                userDetails.getPassword()
                        )? ResponseEntity.ok(jwtUtil.generateToken(userDetails)): ResponseEntity.status(HttpStatus.UNAUTHORIZED).build())
                        .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }


}
