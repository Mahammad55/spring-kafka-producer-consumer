package com.example.springkafkaproducerconsumer.consumer;

import com.example.springkafkaproducerconsumer.exception.TestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Component
@Slf4j
public class KafkaConsumer {
    @RetryableTopic(attempts = "4",
            backoff = @Backoff(delay = 2000, multiplier = 2.0, maxDelay = 12000),
            retryTopicSuffix = "-retry-topic",
            dltTopicSuffix = "-dlt-topic",
            topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_INDEX_VALUE,
            include = {TestException.class},
            autoCreateTopics = "true"
    )
    @KafkaListener(topics = {"${kafka.topic.name}"}, groupId = "ms-group-id")
    public void consumer(String message,
                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                         @Header(KafkaHeaders.RECEIVED_PARTITION) String partition,
                         @Header(KafkaHeaders.OFFSET) Long offset,
                         @Header(KafkaHeaders.RECEIVED_KEY) String key,
                         @Header(KafkaHeaders.GROUP_ID) String groupId,
                         @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp) {


        Instant instant = Instant.ofEpochMilli(timestamp);
        String formattedTimestamp = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.getDefault())
                .withZone(ZoneId.systemDefault())
                .format(instant);

        log.info("Consumer Data is: {}", message);
        log.info("Topic: {}", topic);
        log.info("Partition: {}", partition);
        log.info("Offset: {}", offset);
        log.info("Key: {}", key);
        log.info("Group ID: {}", groupId);
        log.info("Timestamp: {}", formattedTimestamp);

        if (!Character.isUpperCase(message.charAt(0)))
            throw new TestException("Message must be start with the upper letter");
    }

    @DltHandler
    public void listenDlt(String message, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) Long offset) {
        log.info("Deleted Data is: {}, Topic: {}, Offset: {}", message, topic, offset);
    }
}
