package com.hkumar.movie_ticket_system.service;

import com.hkumar.movie_ticket_system.entity.Booking;
import com.hkumar.movie_ticket_system.entity.BookingEvent;
import com.hkumar.movie_ticket_system.entity.Seat;
import com.hkumar.movie_ticket_system.event.producer.BookingEventProducer;
import com.hkumar.movie_ticket_system.repository.BookingRepository;
import com.hkumar.movie_ticket_system.repository.SeatRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final SeatRepository seatRepo;
    private final BookingRepository bookingRepo;
    private final BookingEventProducer bookingEventProducer;
    private final SeatLockService seatLockService;

    @Autowired
    private OfferService offerService;

    @Transactional
    public String bookSeats(List<Long> seatIds, Long showId, String username){

        // STEP 1: LOCK SEATS IN REDIS
        boolean locked = seatLockService.lockSeats(showId, seatIds, (long) username.hashCode());

        if (!locked) {
            throw new RuntimeException("Some seats are temporarily unavailable. Please try again.");
        }

        try {
            // STEP 2: FETCH SEATS FROM DB
            List<Seat> seats = seatRepo.findAllById(seatIds);

            // STEP 3: DB VALIDATION (DOUBLE SAFETY)
            for (Seat seat : seats) {
                if (!seat.getShow().getId().equals(showId)) {
                    throw new RuntimeException("Seat does not belong to this show");
                }
            }
//            for (Seat seat : seats) {
//                if (seat.isBooked()) {
//                    throw new RuntimeException("Seat already booked: " + seat.getSeatNumber());
//                }
//            }

            // STEP 4: APPLY OFFER (OPTIONAL - SIMPLE)
            // double finalPrice = offerService.applyDiscount(...);

            // STEP 5: MARK AS BOOKED
            for (Seat seat : seats) {
                seat.setBooked(true);
            }
            seatRepo.saveAll(seats);

            // STEP 6: CREATE BOOKING
            Booking booking = new Booking();
            booking.setUsername(username);
            booking.setSeats(seats);
            booking.setBookingTime(LocalDateTime.now());
            // booking.setTotalPrice(finalPrice);

            bookingRepo.save(booking);

            // STEP 7: KAFKA EVENT
            bookingEventProducer.sendBookingEvent(
                    new BookingEvent(booking.getId(), username, seatIds)
            );

            return "Booking successful";

        } catch (Exception ex) {

            // STEP 8: RELEASE LOCK ON FAILURE
            seatLockService.releaseSeatsBySeatIds(showId, seatIds);

            throw ex;
        }

        //NOTE:
        // We are NOT releasing lock on success because:
        // seats are now permanently booked in DB
    }
}