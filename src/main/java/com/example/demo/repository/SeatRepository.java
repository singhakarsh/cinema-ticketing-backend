package com.example.demo.repository;

import com.example.demo.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    // Adding 'OrderBySeatNumberAsc' tells Spring to add 'ORDER BY seat_number ASC'
    // to the SQL query automatically!
    List<Seat> findByShowtimeIdOrderBySeatNumberAsc(Long showtimeId);
}