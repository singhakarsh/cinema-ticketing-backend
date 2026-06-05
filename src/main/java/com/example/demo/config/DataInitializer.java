package com.example.demo.config;

import com.example.demo.model.Movie;
import com.example.demo.model.Seat;
import com.example.demo.model.Showtime;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.SeatRepository;
import com.example.demo.repository.ShowtimeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository; // 1. Bring in our Seat database manager

    // 2. Add SeatRepository to the constructor parameter list below
    public DataInitializer(MovieRepository movieRepository,
            ShowtimeRepository showtimeRepository,
            SeatRepository seatRepository) {
        this.movieRepository = movieRepository;
        this.showtimeRepository = showtimeRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (movieRepository.count() == 0) {
            System.out.println("🎬 Database is empty! Injecting premier sample data...");

            // Save Movies
            Movie sciFi = new Movie("Interstellar", "Sci-Fi", 169, "English");
            Movie action = new Movie("The Dark Knight", "Action", 152, "English");
            Movie anime = new Movie("Spirited Away", "Animation", 125, "Japanese");

            movieRepository.save(sciFi);
            movieRepository.save(action);
            movieRepository.save(anime);

            // Save Showtime
            Showtime eveningShow = new Showtime(sciFi, "Audi Screen 1", LocalDateTime.of(2026, 6, 10, 18, 30), 250.00);
            showtimeRepository.save(eveningShow);
            System.out.println("✅ Successfully saved movies and linked showtime!");

            // 3. NEW STEP: Automatically generate a row of seats for this specific show!
            System.out.println("💺 Generating empty seats for Audi Screen 1...");

            // Let's create seats A-1 through A-5 using a simple loop
            for (int i = 1; i <= 5; i++) {
                String seatNo = "A-" + i; // This will create "A-1", "A-2", etc.
                Seat ticketSeat = new Seat(eveningShow, seatNo); // Linked directly to our showtime
                seatRepository.save(ticketSeat);
            }

            System.out.println("✅ Seat generation complete! 5 empty seats are now live and unreserved.");
        }
    }
}