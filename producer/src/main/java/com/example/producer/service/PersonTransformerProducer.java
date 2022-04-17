package com.example.producer.service;

import domain.Person;
import dto.PersonDto;
import org.springframework.stereotype.Service;
import service.PersonTransformer;

import java.util.UUID;

@Service
public class PersonTransformerProducer implements PersonTransformer {

    @Override
    public Person transform(PersonDto dto) {
        return Person.builder()
                .id(UUID.randomUUID().toString())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .username(dto.getUsername())
                .build();
    }
}
