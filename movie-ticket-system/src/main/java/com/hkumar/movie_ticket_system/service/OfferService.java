package com.hkumar.movie_ticket_system.service;

import com.hkumar.movie_ticket_system.entity.Seat;
import com.hkumar.movie_ticket_system.entity.Show;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class OfferService {

    public double applyDiscount(List<Seat> seats, Show show, double totalPrice) {

        double finalPrice = totalPrice;

        // Rule 1: Afternoon discount (20%)
        if (isAfternoonShow(show)) {
            finalPrice = finalPrice * 0.8;
        }

        // Rule 2: 3rd ticket discount (50% on 3rd ticket only)
        finalPrice = applyThirdTicketDiscount(seats, finalPrice);

        return finalPrice;
    }

    private boolean isAfternoonShow(Show show) {
        LocalDateTime showTime = show.getShowTime();

        return true;//will add logic accordingly
    }

    private double applyThirdTicketDiscount(List<Seat> seats, double price) {

        if (seats.size() < 3) return price;

        // assuming equal seat price for simplicity
        double seatPrice = price / seats.size();

        // 50% off on 3rd ticket only
        double discount = seatPrice * 0.5;

        return price - discount;
    }
}
