package com.example.springkafkaproducerconsumer.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;

    private String name;

    private String surname;

    private String address;
}
