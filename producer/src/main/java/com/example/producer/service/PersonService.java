package com.example.producer.service;

import com.example.producer.producer.MessageProducer;
import domain.Person;
import dto.PersonDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PersonService {

    private static final int LARGE_BATCH_SIZE = 100000;

    private final PersonGeneratorProducer personGeneratorProducer;
    private final PersonTransformerProducer transformerProducer;
    private final MessageProducer messageProducer;

    public Person createCustomPerson(PersonDto personDto) {
        Person person = transformerProducer.transform(personDto);
        messageProducer.sendMessage(person);
        log.info("create person = {}", personDto);
        return person;
    }

    public ResponseEntity<String> generateBatchUsers(int limit) {
        personGeneratorProducer.generateUsers(limit)
                .forEach(messageProducer::sendMessage);
        log.info("create batch of persons size of {}", limit);
        return ResponseEntity.ok("created " + limit + " persons ");
    }

    public ResponseEntity<String> generateLargeBatchUsers() {
        personGeneratorProducer.generateUsers(LARGE_BATCH_SIZE)
                .forEach(messageProducer::sendMessage);
        log.info("create batch of persons size of {}", LARGE_BATCH_SIZE);
        return ResponseEntity.ok("created " + LARGE_BATCH_SIZE + " persons ");
    }
}
