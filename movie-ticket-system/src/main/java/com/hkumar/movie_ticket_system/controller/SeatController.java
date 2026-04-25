package com.hkumar.movie_ticket_system.controller;

import com.hkumar.movie_ticket_system.entity.Seat;
import com.hkumar.movie_ticket_system.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/show/{showId}")
    public ResponseEntity<List<Seat>> addSeatsToShow(
            @PathVariable Long showId,
            @RequestBody List<String> seatNumbers) {
        return ResponseEntity.ok(seatService.addSeatsToShow(showId, seatNumbers));
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<Seat>> getSeatsByShow(@PathVariable Long showId) {
        return ResponseEntity.ok(seatService.getSeatsByShow(showId));
    }

    @GetMapping("/show/{showId}/available")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable Long showId) {
        return ResponseEntity.ok(seatService.getAvailableSeatsByShow(showId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeatById(id));
    }
}
