package com.waterapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WaterReservoirApplication {

    public static void main(String[] args) {
        SpringApplication.run(WaterReservoirApplication.class, args);
    }
}
