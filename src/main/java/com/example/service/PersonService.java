package com.example.service;


import com.example.repo.PeopleRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Service
public class PersonService implements ReactiveUserDetailsService {

    private final PeopleRepository peopleRepository;

    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
       return peopleRepository.findByUsername(username)
               .map(person ->  new User(person.getUsername(), person.getPassword(), List.of(new SimpleGrantedAuthority(person.getRole()))));
    }
}
