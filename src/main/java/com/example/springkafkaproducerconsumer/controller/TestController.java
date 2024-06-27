package com.example.springkafkaproducerconsumer.controller;

import com.example.springkafkaproducerconsumer.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping("/{message}")
    public ResponseEntity<String> produceMessage(@PathVariable String message) {
        return ResponseEntity.ok(testService.produceMessage(message));
    }
}
