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

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonService implements ReactiveUserDetailsService {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final PeopleRepository peopleRepository;
    private final ItemRepository itemRepository;

    public PersonService(PasswordEncoder passwordEncoder, PeopleRepository peopleRepository, ItemRepository itemRepository) {
        this.passwordEncoder = passwordEncoder;
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

    public void registerUser(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("USER_ROLE");
        peopleRepository.save(person).subscribe();
    }


    private Map<String, Person> data;

    @PostConstruct
    public void init() {
        data = new HashMap<>();

        //username:password -> user:user
        data.put("user", new Person("user", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY="));

        //username:password -> admin:admin
        data.put("admin", new Person("admin", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w="));
    }

//    Mono<String> getRoleOfUser(String username) {
//        return peopleRepository.getPersonRole(username);
//
//    }

}
