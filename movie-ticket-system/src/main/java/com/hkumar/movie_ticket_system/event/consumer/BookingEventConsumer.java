package com.hkumar.movie_ticket_system.event.consumer;

import com.hkumar.movie_ticket_system.entity.BookingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookingEventConsumer {
    @KafkaListener(topics = "booking-topic", groupId = "booking-group")
    public void consume(BookingEvent event) {
        log.info("Received booking event: {}", event);
    }
}
