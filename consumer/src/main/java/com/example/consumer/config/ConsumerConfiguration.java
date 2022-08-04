package com.example.consumer.config;

import com.example.consumer.listener.KafkaListeners;
import domain.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@Slf4j
public class ConsumerConfiguration {

    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;

    public ConsumerFactory<String, Person> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "consuming");

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        Map<String, Class<?>> classMap = new HashMap<>();
        classMap.put("name.nicholasgribanov.dto.Data", Person.class);
        typeMapper.setIdClassMapping(classMap);
        typeMapper.addTrustedPackages("*");

        JsonDeserializer<Person> jsonDeserializer = new JsonDeserializer<>(Person.class);
        jsonDeserializer.setTypeMapper(typeMapper);
        jsonDeserializer.setUseTypeMapperForKey(true);

        ErrorHandlingDeserializer<Person> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(jsonDeserializer);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Person> kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
                                                                                                 ObjectProvider<ConsumerFactory<Object, Object>> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Person> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setCommonErrorHandler(errorHandler());
        return factory;
    }

    @Bean
    public DefaultErrorHandler errorHandler() {
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((r, ex) ->
                log.error("Kafka consumer exception cannot read record"),
                new FixedBackOff(0L, 2L));
        errorHandler.setLogLevel(KafkaException.Level.DEBUG);
        return errorHandler;
    }

    @Bean
    public KafkaListeners messageListener() {
        return new KafkaListeners();
    }

}
