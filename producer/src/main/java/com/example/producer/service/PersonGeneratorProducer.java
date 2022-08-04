package com.example.producer.service;

import domain.Role;
import domain.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import service.PersonGenerator;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class PersonGeneratorProducer implements PersonGenerator {

    private final List<Role> roles;

    @Override
    public Person generateUser() {
        return Person.builder()
                .id(UUID.randomUUID())
                .email(UUID.randomUUID().toString().substring(26) + "@" + "producer.com")
                .password(UUID.randomUUID().toString().substring(16))
                .role(roles.get((int)(Math.random() * (roles.size()))).name())
                .username("person - [" + UUID.randomUUID().toString().substring(26) + "]")
                .build();
    }

    @Override
    public List<Person> generateUsers(int limit) {
        return IntStream.iterate(0, i -> i++)
                .limit(limit)
                .mapToObj(i -> generateUser())
                .collect(Collectors.toList());
    }
}
