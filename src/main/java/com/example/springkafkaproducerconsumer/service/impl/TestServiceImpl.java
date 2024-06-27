package com.example.springkafkaproducerconsumer.service.impl;

import com.example.springkafkaproducerconsumer.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestServiceImpl implements TestService {
    @Value("${kafka.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public String produceMessage(String message) {
        try {
            CompletableFuture<SendResult<String, Object>> sendResult = kafkaTemplate.send(topicName, "test", message);
            sendResult.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Data successfully sending to kafka");
                } else {
                    log.error("There occur error");
                }
            });

        } catch (Exception e) {
            log.info("Exception is -- {}", e.getMessage());
        }

        return "OK";
    }
}
