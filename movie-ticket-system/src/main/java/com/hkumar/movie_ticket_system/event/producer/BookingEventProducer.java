package com.hkumar.movie_ticket_system.event.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public BookingEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookingEvent(String message) {
        try {
            kafkaTemplate.send("booking-topic", message);
        } catch (Exception e) {
            System.out.println("Kafka not available, skipping event: " + message);
        }
    }
}
