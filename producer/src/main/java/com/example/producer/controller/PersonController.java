package com.example.producer.controller;

import com.example.producer.service.PersonService;
import domain.Person;
import dto.PersonDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    private final PersonService personService;

    @PostMapping()
    public Person createPerson(@RequestBody PersonDto personDto) {
        return personService.createCustomPerson(personDto);
    }

    @PostMapping("{limit}")
    public ResponseEntity<String> generateCustomBatchPersons(@PathVariable Integer limit) {
        return personService.generateBatchUsers(limit);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> generateLargeBatchPersons() {
        return personService.generateLargeBatchUsers();
    }
}
