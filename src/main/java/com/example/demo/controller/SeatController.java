package com.example.demo.controller;

import com.example.demo.model.Seat;
import com.example.demo.repository.SeatRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private final SeatRepository seatRepository;

    public SeatController(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    // This endpoint fetches the real-time seat rows for a specific showtime ID
    @GetMapping("/showtime/{showtimeId}")
    public List<Seat> getSeatsByShowtime(@PathVariable Long showtimeId) {
        // Update this line to call the new ordered method name
        return seatRepository.findByShowtimeIdOrderBySeatNumberAsc(showtimeId);
    }
}