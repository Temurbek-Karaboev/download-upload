package com.example.repo;


import com.example.models.Person;
import liquibase.pro.packaged.Q;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Repository
public interface PeopleRepository extends R2dbcRepository<Person, Integer> {
    Mono<Person> findByUsername(String username);
//    @Query("SELECT Role FROM person p JOIN role ON p.role_id = role.id WHERE p.username =:username; ")
//    Mono<String> getPersonRole(String username);

    @Query("SELECT p.role FROM person p where p.username =:username")
    Mono<String> getRoleByUsername(String username);



}
