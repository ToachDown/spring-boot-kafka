package com.example.consumer.repository;

import domain.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, String> {

    List<Person> findAll();
}
