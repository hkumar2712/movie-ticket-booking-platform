package com.hkumar.movie_ticket_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
public class MovieTicketSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieTicketSystemApplication.class, args);
    }

}
