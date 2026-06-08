package com.example.demo.controller;

import com.example.demo.model.Showtime;
import com.example.demo.repository.ShowtimeRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    private final ShowtimeRepository showtimeRepository;

    public ShowtimeController(ShowtimeRepository showtimeRepository) {
        this.showtimeRepository = showtimeRepository;
    }

    @GetMapping
    public List<Showtime> getAllShowtimes() {
        return showtimeRepository.findAll();
    }

    // Capitalized the 'I' to make it findByMovieId
    @GetMapping("/movie/{movieId}")
    public List<Showtime> getShowtimesByMovie(@PathVariable Long movieId) {
        return showtimeRepository.findByMovieId(movieId);
    }
}