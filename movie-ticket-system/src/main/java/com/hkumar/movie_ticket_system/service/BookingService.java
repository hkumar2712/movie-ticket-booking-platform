package com.hkumar.movie_ticket_system.service;

import com.hkumar.movie_ticket_system.entity.Booking;
import com.hkumar.movie_ticket_system.entity.Seat;
import com.hkumar.movie_ticket_system.event.producer.BookingEventProducer;
import com.hkumar.movie_ticket_system.repository.BookingRepository;
import com.hkumar.movie_ticket_system.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final SeatRepository seatRepo;
    private final BookingRepository bookingRepo;
    private final BookingEventProducer bookingEventProducer;

    @Transactional
    public String bookSeats(List<Long> seatIds, String username) {

        List<Seat> seats = seatRepo.findAllById(seatIds);

        // checking already booked
        for (Seat seat : seats) {
            if (seat.isBooked()) {
                throw new RuntimeException("Seat already booked: " + seat.getSeatNumber());
            }
        }

        // mark as booked
        for (Seat seat : seats) {
            seat.setBooked(true);
        }
        seatRepo.saveAll(seats);

        Booking booking = new Booking();
        booking.setUsername(username);
        booking.setSeats(seats);
        booking.setBookingTime(LocalDateTime.now());

        bookingRepo.save(booking);
        bookingEventProducer.sendBookingEvent(
                "Booking done by user: " + username + " for seats: " + seatIds
        );

        return "Booking successful";
    }
}
