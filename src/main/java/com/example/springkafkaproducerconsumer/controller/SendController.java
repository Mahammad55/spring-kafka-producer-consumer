package com.example.springkafkaproducerconsumer.controller;

import com.example.springkafkaproducerconsumer.dto.UserDto;
import com.example.springkafkaproducerconsumer.service.SendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class SendController {
    private final SendService sendService;

    @PostMapping("/{text}")
    public ResponseEntity<String> produceTest(@PathVariable String text) {
        sendService.sendData(text);
        return ResponseEntity.ok("Text send successfully");
    }

    @PostMapping("/user")
    public ResponseEntity<String> produceTest(@RequestBody UserDto userDto) {
        sendService.sendData(userDto);
        return ResponseEntity.ok("User dto send successfully");
    }
}
