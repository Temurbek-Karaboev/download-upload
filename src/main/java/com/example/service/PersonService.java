package com.example.service;

import com.example.models.Item;
import com.example.repo.ItemRepository;
import com.example.repo.PeopleRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PersonService implements ReactiveUserDetailsService {

    private final PeopleRepository peopleRepository;
    private final ItemRepository itemRepository;

    public PersonService(PeopleRepository peopleRepository, ItemRepository itemRepository) {
        this.peopleRepository = peopleRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return peopleRepository.findByUsername(username)
                .map(person -> new User(person.getUsername(), person.getPassword(), List.of(new SimpleGrantedAuthority(person.getRole()))));
    }

    public Mono<Object> addData(String username){
        return peopleRepository.findByUsername(username)
                .flatMap(data->{
                    Item item=new Item();
                    item.setUsername(data.getUsername());
                    return itemRepository.save(item);
                });
    }
}
