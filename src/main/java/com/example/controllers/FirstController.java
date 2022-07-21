package com.example.controllers;

import com.example.models.Person;
import com.example.models.security.AuthRequest;
import com.example.models.security.AuthResponse;
import com.example.repo.PeopleRepository;
import com.example.security.JWTUtil;
import com.example.security.PBKDF2Encoder;
import com.example.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;


@Controller
public class FirstController {
    private final PersonService personService;
    private final JWTUtil jwtUtil;
    private final PBKDF2Encoder passwordEncoder;
    private final ObjectMapper objectMapper;

    private final PeopleRepository peopleRepository;

    public FirstController(PersonService personService, JWTUtil jwtUtil, PBKDF2Encoder passwordEncoder, ObjectMapper objectMapper, PeopleRepository peopleRepository) {
        this.personService = personService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
        this.peopleRepository = peopleRepository;
    }

    @GetMapping("/")
    public String redirectPage() {
        return "redirect";
    }

    @GetMapping("/upload-page")
    public String uploadPage() {
        return "upload";
    }


    @GetMapping("/download-page")
    public String downloadPage() {
        return "download";
    }



    @GetMapping("/menu")
    public Mono<Void> redirectMenu(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        response.getHeaders().setLocation(URI.create("/"));
        return response.setComplete();
    }

    @PostMapping("/login-page")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return personService.findByUsername(ar.getUsername())
                .filter(userDetails -> passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword()))
                .flatMap(userDetails -> {
                    return peopleRepository.getRoleByUsername(userDetails.getUsername())
                            .flatMap(role -> Mono.just(ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(objectMapper.convertValue(userDetails, Person.class), role)))));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }


    @PostMapping("/registration")
    public String createPerson(@RequestBody Person person) {
        System.out.println(person);
//        if(personService.findByUsername(person.getUsername()) == null) {
        personService.registerUser(person);
        return "redirect:/login";
//        }

//        return "redirect:/register";

    }


    @GetMapping("/login")
    public String login() {
        return "loginpage";
    }

}

//        return "redirect:/login";


