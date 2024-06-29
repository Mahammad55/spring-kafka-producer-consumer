package com.example.springkafkaproducerconsumer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.topic.name}")
    private String topicName;

    @Value("${kafka.user-topic.name}")
    private String generalTopicName;

    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name(topicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic newTopic() {
        return new NewTopic(generalTopicName, 5, (short) 1);
    }
}