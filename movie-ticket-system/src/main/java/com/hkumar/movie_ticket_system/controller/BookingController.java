package com.hkumar.movie_ticket_system.controller;

import com.hkumar.movie_ticket_system.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public String book(@RequestBody List<Long> seatIds,
                       @AuthenticationPrincipal String username) {//AuthenticationPrincipal: because we stored username in JWT

        return bookingService.bookSeats(seatIds, username);
    }
}
