package com.example.service;

import com.example.models.Item;
import com.example.models.Person;
import com.example.repo.ItemRepository;
import com.example.repo.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PersonService implements ReactiveUserDetailsService {

    private final PeopleRepository peopleRepository;
    private final ItemRepository itemRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public PersonService(PeopleRepository peopleRepository, ItemRepository itemRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.itemRepository = itemRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return peopleRepository.findByUsername(username)
                .map(person -> new User(person.getUsername(), person.getPassword(), List.of(new SimpleGrantedAuthority(person.getRole()))));
    }


    public Mono<Object> addData(String username) {
        return peopleRepository.findByUsername(username)
                .flatMap(data -> {
                    Item item = new Item();
                    item.setUsername(data.getUsername());
                    return itemRepository.save(item);
                });
    }

    public void registerUser(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        peopleRepository.save(person).subscribe();
    }


}
