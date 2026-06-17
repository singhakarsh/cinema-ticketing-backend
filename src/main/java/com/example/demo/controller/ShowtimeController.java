package com.example.demo.controller;

import com.example.demo.model.Showtime;
import com.example.demo.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/public/showtimes")
@CrossOrigin(origins = "*")
public class ShowtimeController {

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @GetMapping
    public ResponseEntity<List<Showtime>> getAllShowtimes() {
        // Fetches all showtimes from PostgreSQL
        List<Showtime> showtimes = showtimeRepository.findAll();
        return ResponseEntity.ok(showtimes);
    }

    // Capitalized the 'I' to make it findByMovieId
    @GetMapping("/movie/{movieId}")
    public List<Showtime> getShowtimesByMovie(@PathVariable Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }
}