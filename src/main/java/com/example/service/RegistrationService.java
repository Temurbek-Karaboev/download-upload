package com.example.service;

import com.example.models.Person;
import com.example.repo.PeopleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;


    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(Person person) {
        String encoded =passwordEncoder.encode(person.getPassword());
        person.setPassword(encoded);
        System.out.println(encoded);
        person.setRole("ROLE_USER");
        peopleRepository.save(person).subscribe();
    }

}
