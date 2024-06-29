package com.example.springkafkaproducerconsumer.consumer;

import com.example.springkafkaproducerconsumer.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaSendConsumer {
    @KafkaListener(topics = {"${kafka.user-topic.name}"}, groupId = "user-group")
    public void consumer(String text) {
        log.info("Text message accepted: {}", text);
    }

    @KafkaListener(topics = {"${kafka.user-topic.name}"}, groupId = "user-group")
    public void consumer(UserDto userDto) {
        log.info("User dto message accepted: {}", userDto);
    }
}
