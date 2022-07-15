package com.example.repo;


import com.example.models.Person;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface PeopleRepository extends R2dbcRepository<Person, Integer> {
//    @Query("SELECT * FROM person p where p.username =:username")
    Mono<Person> findByUsername(String username);

}
