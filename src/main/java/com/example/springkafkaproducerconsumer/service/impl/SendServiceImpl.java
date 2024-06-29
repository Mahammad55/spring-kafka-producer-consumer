package com.example.springkafkaproducerconsumer.service.impl;

import com.example.springkafkaproducerconsumer.service.SendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendServiceImpl implements SendService {
    @Value("${kafka.user-topic.name}")
    private String generalTopicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public <T> void sendData(T data) {
        Message<T> message = MessageBuilder
                .withPayload(data)
                .setHeader(KafkaHeaders.TOPIC, generalTopicName)
                .setHeader(KafkaHeaders.PARTITION, 4)
                .build();

        kafkaTemplate.send(message);
        log.info("{} successfully sending to kafka", data);
    }
}
