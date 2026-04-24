package com.hkumar.movie_ticket_system.controller;

import com.hkumar.movie_ticket_system.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public String book(@RequestParam Long showId,
                       @RequestBody List<Long> seatIds,
                       @AuthenticationPrincipal String username) {

        return bookingService.bookSeats(seatIds, showId, username);
    }
}
