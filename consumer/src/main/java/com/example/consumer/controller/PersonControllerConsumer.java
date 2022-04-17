package com.example.consumer.controller;

import com.example.consumer.service.PersonSaverConsumer;
import domain.Person;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/persons")
public class PersonControllerConsumer {

    private final PersonSaverConsumer personServiceConsumer;

    @GetMapping()
    public List<Person> getAllUserDate() {
        return personServiceConsumer.getAllUsersData();
    }
}
