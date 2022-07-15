package com.example.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.net.URI;


@Controller
public class FirstController {
    @GetMapping("/")
    public String redirectPage() {
        return "redirect";
    }

    @GetMapping("/upload")
    public String uploadPage() {
        return "upload";
    }

    @GetMapping("/upload/file/single")
    public Mono<Void> redirectOne(ServerHttpResponse response){
        response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
        response.getHeaders().setLocation(URI.create("/"));
        return response.setComplete();
    }

}
