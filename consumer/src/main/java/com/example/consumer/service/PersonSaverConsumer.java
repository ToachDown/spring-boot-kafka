package com.example.consumer.service;

import com.example.consumer.repository.PersonRepository;
import domain.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PersonSaverConsumer implements PersonSaver {

    private final PersonRepository personRepository;

    @Override
    public Person saveMessage(Person data) {
        return personRepository.save(data);
    }

    public List<Person> getAllUsersData() {
        return personRepository.findAll()
                .stream().limit(1000).collect(Collectors.toList());
    }
}
