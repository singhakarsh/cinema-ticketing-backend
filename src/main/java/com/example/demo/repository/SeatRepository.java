package com.example.demo.repository;

import com.example.demo.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByShowtimeIdOrderBySeatNumberAsc(Long showtimeId);

    List<Seat> findByShowtimeIdOrderByIdAsc(Long showtimeId);

    // 🌟 No custom boolean finder methods here! This keeps the app startup
    // perfectly safe.
}