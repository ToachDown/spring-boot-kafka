package com.example.producer.producer;

import domain.Person;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;

    @Value(value = "${spring.kafka.topic.name}")
    private String topicName;


    public void sendMessage(Person user) {
        ListenableFuture<SendResult<String, Person>> future = kafkaTemplate.send(topicName, user);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Person>>() {

            @Override
            public void onFailure(Throwable throwable) {
                log.error("Unable to send message = {} dut to: {}", user, throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Person> stringDataSendResult) {
                log.info("Sent Message = {} with offset = {}", user, stringDataSendResult.getRecordMetadata().offset());
            }
        });
    }
}
