package com.example.demo.controller;

import com.example.demo.model.Seat;
import com.example.demo.repository.SeatRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final SeatRepository seatRepository;

    public BookingController(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    // This endpoint allows a user to book a specific seat ID via a POST request
    @PostMapping("/book/{seatId}")
    public String bookSeat(@PathVariable Long seatId) {

        // 1. Check if the seat actually exists in our theater database
        Seat seat = seatRepository.findById(seatId).orElse(null);

        if (seat == null) {
            return "❌ Error: This seat does not exist in our theater system.";
        }

        // 2. Check if another customer already reserved it
        if (seat.isReserved()) {
            return "❌ Sorry, Seat " + seat.getSeatNumber() + " is already sold out!";
        }

        // 3. If it's free, reserve it and save the change to PostgreSQL!
        seat.setReserved(true);
        seatRepository.save(seat);

        return "🎟️ Success! Ticket confirmed for Seat " + seat.getSeatNumber() + ". Enjoy your movie!";
    }
}