package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.Showtime;
import com.example.demo.service.CinemaManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*") // Prevents unexpected browser CORS request blocks
public class AdminController {

    private final CinemaManagementService cinemaService;

    // Direct Constructor Injection
    public AdminController(CinemaManagementService cinemaService) {
        this.cinemaService = cinemaService;
    }

    // 1. Endpoint to add a brand new movie
    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie createdMovie = cinemaService.createMovie(movie);
        return ResponseEntity.ok(createdMovie);
    }

    // 2. Data Contract DTO for Flattened Request Body Mapping
    public static class ShowtimeFormRequest {
        public Long movieId;
        public String screenName;
        public LocalDateTime startTime;
        public double ticketPrice;
    }

    // 3. Endpoint to create a showtime with automated seating allocations
    @PostMapping("/showtimes")
    public ResponseEntity<String> addShowtime(@RequestBody ShowtimeFormRequest request) {
        if (request.movieId == null) {
            return ResponseEntity.badRequest().body("❌ Error: Missing required Movie ID mapping.");
        }

        Movie fullMovie = cinemaService.getMovieById(request.movieId);
        if (fullMovie == null) {
            return ResponseEntity.badRequest().body("❌ Error: Selected movie does not exist inside our database.");
        }

        Showtime showtime = new Showtime();
        showtime.setMovie(fullMovie);
        showtime.setScreenName(request.screenName);
        showtime.setStartTime(request.startTime);
        showtime.setTicketPrice(request.ticketPrice);

        cinemaService.createShowtime(showtime);

        return ResponseEntity.ok("🚀 Showtime created successfully! 32 seats have been automatically allocated.");
    }
}