package com.example.consumer.listener;

import com.example.consumer.service.PersonSaver;
import domain.Person;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;

public class KafkaListeners {

    private static final String TOPIC = "fish";

    @Autowired
    private PersonSaver dataService;

    @KafkaListener(topics = TOPIC, topicPartitions = {@TopicPartition(topic = "fish", partitions = {"1"})}, containerFactory = "kafkaListenerContainerFactory", groupId = ConsumerConfig.GROUP_ID_CONFIG)
    public void listener(Person data) {
        System.out.println("Recieved message: " + data);
 //       dataService.saveMessage(data);
    }
}
