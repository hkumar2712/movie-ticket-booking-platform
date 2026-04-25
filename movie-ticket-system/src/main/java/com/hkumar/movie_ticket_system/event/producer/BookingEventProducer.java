package com.hkumar.movie_ticket_system.event.producer;

import com.hkumar.movie_ticket_system.entity.BookingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingEventProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public BookingEventProducer(
            @Autowired(required = false) KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendBookingEvent(BookingEvent event) {
        if (kafkaTemplate == null) {
            System.out.println("Kafka not configured, skipping event: " + event);
            return;
        }
        try {
            kafkaTemplate.send("booking-topic", event.getUsername());
        } catch (Exception e) {
            System.out.println("Kafka not available, skipping event: " + event);
        }
    }
}
