package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.model.Showtime;
import com.example.demo.model.Seat;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ShowtimeRepository;
import com.example.demo.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CinemaManagementService {

    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    public CinemaManagementService(MovieRepository movieRepository,
            ShowtimeRepository showtimeRepository,
            SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    // 1. Save a new movie entry
    @Transactional
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    // 2. Create a showtime AND automatically generate its 32-seat matrix
    @Transactional
    public Showtime createShowtime(Showtime showtime) {
        // Save the showtime to get an ID from PostgreSQL
        Showtime savedShowtime = showtimeRepository.save(showtime);

        // Define our standard public-ready 32-seat theater hall grid
        char[] rows = { 'A', 'B', 'C', 'D' };
        for (char row : rows) {
            for (int number = 1; number <= 8; number++) {
                String seatNumber = row + "-" + number;
                Seat seat = new Seat(savedShowtime, seatNumber);
                seatRepository.save(seat);
            }
        }

        return savedShowtime;
    }

    // Add this method inside CinemaManagementService class block if not present
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    // Add this method inside your CinemaManagementService class block
    @org.springframework.transaction.annotation.Transactional
    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }
}