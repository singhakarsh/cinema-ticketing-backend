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
            System.out.println("🎬 Database is empty! Injecting premium themed movie data...");

            // We pass: (Title, Genre, Duration, Language, PosterImage, BannerImage)
            Movie interstellar = new Movie(
                    "Interstellar", "Sci-Fi", 169, "English",
                    "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=400", // Starry sky poster
                    "https://images.unsplash.com/photo-1534796636912-3b95b3ab5986?w=1200" // Massive deep space nebula
                                                                                          // banner
            );

            Movie batman = new Movie(
                    "The Dark Knight", "Action", 152, "English",
                    "https://images.unsplash.com/photo-1509248961158-e54f6934749c?w=400", // Dark moody poster
                    "https://images.unsplash.com/photo-1518241353330-0f7941c2d9b5?w=1200" // Dark city skyline banner
            );

            Movie anime = new Movie(
                    "Spirited Away", "Animation", 125, "Japanese",
                    "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=400", // Creative artistic poster
                    "https://images.unsplash.com/photo-1528164344705-47542687000d?w=1200" // Beautiful classic Japan
                                                                                          // neon/nature banner
            );

            movieRepository.save(interstellar);
            movieRepository.save(batman);
            movieRepository.save(anime);

            // Create a Relational Showtime for Interstellar (Movie 1)
            Showtime show1 = new Showtime(interstellar, "Audi Screen 1", LocalDateTime.of(2026, 6, 10, 18, 30), 250.00);
            showtimeRepository.save(show1);

            // NEW: Create a Relational Showtime for The Dark Knight (Movie 2)
            Showtime show2 = new Showtime(batman, "IMAX Screen 2", LocalDateTime.of(2026, 6, 10, 21, 00), 350.00);
            showtimeRepository.save(show2);

            // --- Generate Expanded Seat Matrix for Showtime 1 (Interstellar) ---
            char[] rows = { 'A', 'B', 'C', 'D' };
            for (char row : rows) {
                for (int number = 1; number <= 8; number++) {
                    String seatNumber = row + "-" + number; // Creates "A-1", "A-2" ... "D-8"
                    seatRepository.save(new Seat(show1, seatNumber));
                }
            }

            // --- Generate Expanded Seat Matrix for Showtime 2 (The Dark Knight) ---
            for (char row : rows) {
                for (int number = 1; number <= 8; number++) {
                    String seatNumber = row + "-" + number;
                    seatRepository.save(new Seat(show2, seatNumber));
                }
            }

            System.out.println(
                    "🚀 Multi-row seating matrix initialized! 32 premium luxury seats allocated per showtime.");
        }
    }
}