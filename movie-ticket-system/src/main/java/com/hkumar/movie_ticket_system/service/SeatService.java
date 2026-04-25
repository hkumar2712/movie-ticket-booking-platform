package com.hkumar.movie_ticket_system.service;

import com.hkumar.movie_ticket_system.entity.Seat;
import com.hkumar.movie_ticket_system.entity.Show;
import com.hkumar.movie_ticket_system.repository.SeatRepository;
import com.hkumar.movie_ticket_system.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    public List<Seat> addSeatsToShow(Long showId, List<String> seatNumbers) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + showId));

        List<Seat> seats = new ArrayList<>();
        for (String seatNumber : seatNumbers) {
            Seat seat = new Seat();
            seat.setSeatNumber(seatNumber);
            seat.setShow(show);
            seat.setBooked(false);
            seats.add(seat);
        }
        return seatRepository.saveAll(seats);
    }

    public List<Seat> getSeatsByShow(Long showId) {
        return seatRepository.findByShowId(showId);
    }

    public List<Seat> getAvailableSeatsByShow(Long showId) {
        return seatRepository.findByShowIdAndBooked(showId, false);
    }

    public Seat getSeatById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found with id: " + id));
    }
}
