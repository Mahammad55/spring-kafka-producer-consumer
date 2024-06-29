package com.example.springkafkaproducerconsumer.service;

public interface SendService {
    <T> void sendData(T data);
}
