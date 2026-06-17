package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.model.Showtime;
import com.example.demo.model.Seat;
import com.example.demo.model.AnalyticsSummaryDTO;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.ShowtimeRepository;
import com.example.demo.repository.SeatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CinemaManagementService {

    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;

    // Direct Constructor Injection for all repositories cleanly
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

    // 3. Find a single movie by its primary ID lookup
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    // 4. Complete Cascading Eraser Execution Method
    @Transactional
    public void deleteMovieById(Long id) {
        movieRepository.deleteById(id);
    }

    // 5. Dynamic Seating Chart Matrix Fetcher
    public List<Seat> getSeatsByShowtimeId(Long showtimeId) {
        return seatRepository.findByShowtimeIdOrderByIdAsc(showtimeId);
    }

    // 6. Real-Time Admin Analytics Aggregator Engine
    // 🌟 Update this method inside your CinemaManagementService.java class block
    // 🌟 Update this specific method inside CinemaManagementService.java
    // 🌟 Replace this method inside CinemaManagementService.java:
    public AnalyticsSummaryDTO getDashboardAnalytics() {
        long totalMovies = movieRepository.count();
        long totalShowtimes = showtimeRepository.count();

        // 1. Grab all seats safely using a built-in JPA method
        List<Seat> allSeats = seatRepository.findAll();

        double totalRevenue = 0.0;
        long totalBookedSeats = 0;

        // 2. Compute state summaries inside a standard Java loop
        for (Seat seat : allSeats) {
            // Using your model's exact method checker name (isReserved)
            if (seat.isReserved()) {
                totalBookedSeats++;
                if (seat.getShowtime() != null) {
                    totalRevenue += seat.getShowtime().getTicketPrice();
                }
            }
        }

        // 3. Calculate Global Occupancy Rate
        long totalAvailableSeatsInSystem = totalShowtimes * 32;

        double occupancyRate = 0.0;
        if (totalAvailableSeatsInSystem > 0) {
            occupancyRate = ((double) totalBookedSeats / totalAvailableSeatsInSystem) * 100.0;
        }

        return new AnalyticsSummaryDTO(totalMovies, totalShowtimes, totalRevenue, occupancyRate);
    }
}