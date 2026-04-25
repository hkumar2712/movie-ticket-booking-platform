package com.hkumar.movie_ticket_system.event.consumer;

import com.hkumar.movie_ticket_system.entity.BookingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@ConditionalOnProperty(name = "app.kafka.enabled", havingValue = "true")
public class BookingEventConsumer {

    @KafkaListener(topics = "booking-topic", groupId = "booking-group")
    public void consume(String message) {   // String, not BookingEvent - matches what producer sends
        log.info("Received booking event: {}", message);
    }
}
