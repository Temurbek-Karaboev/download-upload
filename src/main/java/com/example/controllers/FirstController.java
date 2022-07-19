package com.example.controllers;

import com.example.models.Person;
import com.example.repo.PeopleRepository;
import com.example.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;


@Controller
public class FirstController {


    @Autowired
    private final PersonService personService;

    @Autowired
    private final PeopleRepository peopleRepository;

    public FirstController(PersonService personService, PeopleRepository peopleRepository) {
        this.personService = personService;
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


    @GetMapping("/registration")
    public String registrationPage() {
        System.out.println("1");
        return "register";
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

    }



